package com.echo.echo.domain.text.controller;

import com.echo.echo.common.exception.CustomException;
import com.echo.echo.common.exception.codes.CommonErrorCode;
import com.echo.echo.common.redis.RedisConst;
import com.echo.echo.common.redis.RedisPublisher;
import com.echo.echo.common.util.ObjectStringConverter;
import com.echo.echo.common.websocket.AbstractWebSocketHandler;
import com.echo.echo.domain.channel.ChannelService;
import com.echo.echo.domain.dm.DmService;
import com.echo.echo.domain.text.TextService;
import com.echo.echo.domain.text.dto.TextRequest;
import com.echo.echo.domain.text.dto.TextResponse;
import com.echo.echo.domain.text.dto.TypingRequest;
import com.echo.echo.domain.text.entity.Text;
import com.echo.echo.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j(topic = "textHandler")
@RequiredArgsConstructor
@Component
public class TextWebSocketHandler extends AbstractWebSocketHandler {

	private final TextService textService;
	private final ChannelService channelService;
	private final DmService dmService;
	private final ObjectStringConverter objectStringConverter;
	private final RedisPublisher redisPublisher;

	Long channelId;
	String dmId;

	public Mono<Sinks.EmitResult> sendText(String body) {
		return Mono.fromSupplier(() -> objectStringConverter.stringToObject(body, TextResponse.class))
			.flatMap(response -> response.map(res ->
				(res.getDmId() != null ?
					dmService.getDmSink(res.getDmId()) :
					textService.getSink(res.getChannelId())).tryEmitNext(res))) // DM ID 또는 채널 ID 사용
			.doOnSuccess(emitResult -> {
				if (emitResult.isFailure()) {
					log.error("Redis Sub 메시지 전송 실패: {}", body);
				}
			});
	}

	@Override
	public void doOnSubscribe(Subscription subscription) {
		log.info("{}, @@@@@@@@@@@@ 웹소켓 연결 완료", channelId);
		String channelStr = this.getQueryParams().get("channel");

		Long channelId = channelStr == null ? null : Long.valueOf(channelStr);
		String dmId = this.getQueryParams().get("dmId"); // DM 관련 파라미터

		WebSocketSession session = this.getWebSocketSession();
	}

	@Override
	public void init(WebSocketSession webSocketSession, Map<String, String> queryParams, Mono<User> userMono) {
		super.init(webSocketSession, queryParams, userMono);
		this.channelId = queryParams.get("channel") == null ? null : Long.valueOf(this.getQueryParams().get("channel"));
		this.dmId = queryParams.get("dmId");
	}

	@Override
	public Mono<Boolean> startSession() {
		WebSocketSession webSocketSession = this.getWebSocketSession();

		Flux<TextResponse> dmMono = dmService.loadTextByDmId(dmId);
		Flux<TextResponse> textMono = textService.loadTextByChannelId(channelId);

		if (dmId != null) {
			dmMono.subscribe();
			return Mono.just(true);
		}

		return channelService.checkAndIncrementMemberCount(channelId)
				.flatMap(res -> textMono.flatMap(objectStringConverter::objectToString)
						.map(webSocketSession::textMessage)
						.flatMap(messages -> webSocketSession.send(Mono.just(messages)))
						.then(Mono.just(true)))
				.onErrorResume(e -> {
					log.error("{}은 최대 인원입니다. 입장 불가.", channelId);
					WebSocketMessage errorMessage = webSocketSession.textMessage("{\"msg\": \"" + e.getMessage() + "\"}");
					return webSocketSession.send(Mono.just(errorMessage)).then(webSocketSession.close()).then(Mono.just(false));
				});
//				.doOnError(e -> {
//					WebSocketMessage errorMessage = webSocketSession.textMessage("{\"msg\": \"" + e.getMessage() + "\"}");
//					Mono.when(webSocketSession.send(Mono.just(errorMessage))).subscribe();
//					throw new CustomException(CommonErrorCode.FAIL);
//				})
	}

	@Override
	public void doFinally(SignalType signal) {
		log.info("{}의 웹소켓이 만료되었습니다.", channelId);
		channelService.decrementMemberCount(channelId).subscribe();
	}


	@Override
	public Mono<Void> receive(String payload) {
		return objectStringConverter.stringToObject(payload, TextRequest.class)
				.flatMap(textRequest -> getUserMono().flatMap(user -> {
					if (dmId != null) {
						return dmService.sendTextToDm(Mono.just(textRequest), user.getNickname(), user.getId(), dmId, Text.TextType.TEXT);
					}
					return textService.sendText(Mono.just(textRequest), user.getNickname(), user.getId(), channelId, Text.TextType.TEXT);
				}))
				.flatMap(response -> {
                    response.setHandleType(TextResponse.HandleType.CREATED);
                    return redisPublisher.publish(RedisConst.TEXT.getChannelTopic(), response);
                });
	}

	@Override
	public Flux<String> send() {
		Sinks.Many<TextResponse> textResponseSink = dmId != null ?
				dmService.getDmSink(dmId) : textService.getSink(channelId);

		return textResponseSink.asFlux().flatMap(objectStringConverter::objectToString)
				.doOnError(throwable -> log.error("웹소켓 메시지 변환 간 오류 발생", throwable));
	}

	@Override
	public Mono<Void> sendTyping(String payload) {
		if (channelId != null) {
			Mono<TypingRequest> request = objectStringConverter.stringToObject(payload,
					TypingRequest.class);

			return getUserMono().flatMap(user -> textService.sendTyping(request, user.getNickname(), channelId)
					.flatMap(response -> redisPublisher.publish(RedisConst.TYPING.getChannelTopic(), response)));
		}
		return Mono.empty();
	}
}
