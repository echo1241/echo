package com.echo.echo.common.websocket;

import com.echo.echo.domain.user.entity.User;
import com.echo.echo.security.principal.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class WebSocketHandlerTemplate implements WebSocketHandler {

    private static final String PING_MSG = "$p&ing";
    private static final String TYPING_MSG = "typing";
    private final CommonWebSocketHandler commonWebSocketHandler;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        // 여기에 공통적으로 들어가는 것을 지정합니다.
        Map<String, String> uriQuery = getParamFromSession(session.getHandshakeInfo().getUri());

        commonWebSocketHandler.init();

        // receive()
        Mono<Void> receive = session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .flatMap(payload -> {
                    // ping 처리
                    if (payload.contains(PING_MSG)) {
                        return Mono.empty();
                    // 타이핑 처리
                    } else if (payload.contains(TYPING_MSG)) {
                        return commonWebSocketHandler.sendTyping();
                    // 그 이외
                    } else {
                        return commonWebSocketHandler.receive(getUser(), uriQuery, payload);
                    }
                })
                // 접속 시 처음 실행
                .doOnSubscribe(commonWebSocketHandler::doOnSubscribe)
                // 예외 처리
                .doOnError(err -> {
                    log.info(err.getMessage());
                    commonWebSocketHandler.doOnError(err);
                    session.close();
                })
                // 세션 만료
                .doFinally(signal -> {
                    commonWebSocketHandler.doFinally(signal);
                    session.close();
                })
                .then();

        // send()
        Mono<Void> send = session.send(commonWebSocketHandler.send(uriQuery)
                .map(session::textMessage));

        return Mono.when(receive, send);
    }

    /**
     * url 데이터 파싱
     * @param uri URI 형식 url
     */
    private Map<String, String> getParamFromSession(URI uri) {
        String query = uri.getQuery();
        Map<String, String> queryParam = new HashMap<>();

        String[] pairs = query.split("&");

        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            String key = keyValue[0];
            String value = keyValue.length > 1 ? keyValue[1] : "";
            queryParam.put(key, value);
        }

        return queryParam;
    }

    /**
     * 토큰에 있는 유저에 대한 정보를 가져온다
     */
    private Mono<User> getUser() {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> ((UserPrincipal) (context.getAuthentication().getPrincipal())).getUser());
    }
}
