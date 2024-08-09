package com.echo.echo.domain.video;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.kurento.client.IceCandidate;
import org.kurento.client.KurentoClient;
import org.kurento.client.MediaPipeline;
import org.kurento.client.WebRtcEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import reactor.core.publisher.Mono;

@Component
public class SignalingHandler implements WebSocketHandler {

	private static final Logger log = LoggerFactory.getLogger(SignalingHandler.class);
	private final KurentoClient kurentoClient = KurentoClient.create();
	private final MediaPipeline pipeline = kurentoClient.createMediaPipeline();
	private final Map<String, WebRtcEndpoint> clients = new ConcurrentHashMap<>();

	@Override
	public Mono<Void> handle(WebSocketSession session) {
		return session.receive()
			.doOnNext(message -> {
				JsonObject jsonMessage = JsonParser.parseString(message.getPayloadAsText()).getAsJsonObject();
				String messageType = jsonMessage.get("type").getAsString();

				switch (messageType) {
					case "offer":
						handleOfferMessage(session, jsonMessage);
						break;
					case "candidate":
						handleCandidateMessage(session, jsonMessage);
						break;
					default:
				}
			})
			.doFinally(signalType -> {
				log.info("세션 종료: {}", session.getId());
				cleanupSession(session.getId());
			})
			.then();
	}

	private void handleOfferMessage(WebSocketSession session, JsonObject jsonMessage) {
		log.info("offer 메시지 처리");
		String clientId = session.getId();

		WebRtcEndpoint webRtcEndpoint = new WebRtcEndpoint.Builder(pipeline).build();
		clients.put(clientId, webRtcEndpoint);

		// ICE 후보 수집을 위한 STUN 서버 설정 (클라이언트와 같은 STUN 서버 사용)
		webRtcEndpoint.setStunServerAddress("stun.l.google.com");
		webRtcEndpoint.setStunServerPort(19302);

		String sdpOffer = jsonMessage.get("sdp").getAsString();
		String sdpAnswer = webRtcEndpoint.processOffer(sdpOffer);
		log.info("SDP offer 처리 및 SDP answer 생성: {}, {}", sdpOffer, sdpAnswer);

		JsonObject response = new JsonObject();
		response.addProperty("type", "answer");
		response.addProperty("sdp", sdpAnswer);

		session.send(Mono.just(session.textMessage(response.toString())))
			.doOnError(error -> log.error("SDP answer 전송 오류: {}", error.getMessage()))
			.doOnSuccess(unused -> log.info("SDP answer 전송 성공"))
			.subscribe();

		// ICE 후보 수집 시작
		webRtcEndpoint.gatherCandidates();

		// ICE 후보를 클라이언트에 전송
		webRtcEndpoint.addIceCandidateFoundListener(event -> {
			IceCandidate candidate = event.getCandidate();
			JsonObject candidateMessage = new JsonObject();
			candidateMessage.addProperty("type", "candidate");
			candidateMessage.addProperty("candidate", candidate.getCandidate());
			candidateMessage.addProperty("sdpMid", candidate.getSdpMid());
			candidateMessage.addProperty("sdpMLineIndex", candidate.getSdpMLineIndex());

			session.send(Mono.just(session.textMessage(candidateMessage.toString())))
				.doOnError(error -> log.error("ICE candidate 전송 오류: {}", error.getMessage()))
				.doOnSuccess(unused -> log.info("ICE candidate 전송 성공"))
				.subscribe();
		});
	}

	private void handleCandidateMessage(WebSocketSession session, JsonObject jsonMessage) {
		log.info("ICE candidate 메시지 처리");
		String clientId = session.getId();
		WebRtcEndpoint webRtcEndpoint = clients.get(clientId);

		if (webRtcEndpoint != null) {
			String candidate = jsonMessage.get("candidate").getAsString();
			String sdpMid = jsonMessage.get("sdpMid").getAsString();
			int sdpMLineIndex = jsonMessage.get("sdpMLineIndex").getAsInt();

			IceCandidate iceCandidate = new IceCandidate(candidate, sdpMid, sdpMLineIndex);
			webRtcEndpoint.addIceCandidate(iceCandidate);
			log.info("ICE candidate 추가: {}", candidate);
		} else {
			log.warn("해당 clientId에 대한 WebRtcEndpoint가 존재하지 않음: {}", clientId);
		}
	}

	private void cleanupSession(String clientId) {
		WebRtcEndpoint webRtcEndpoint = clients.remove(clientId);
		if (webRtcEndpoint != null) {
			webRtcEndpoint.release();
		}
	}
}