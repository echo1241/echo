package com.echo.echo.domain.voice;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.WebSocketMessage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@Component
public class VoiceHandler implements WebSocketHandler {

	private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

	@Override
	public Mono<Void> handle(WebSocketSession session) {
		String sessionId = session.getId();
		sessions.put(sessionId, session);

		sessions.values().forEach(s -> {
			if (!s.getId().equals(sessionId)) {
				s.send(Mono.just(s.textMessage("{\"type\":\"new-peer\",\"id\":\"" + sessionId + "\"}"))).subscribe();
			}
		});

		return session.receive()
			.map(WebSocketMessage::getPayloadAsText)
			.flatMap(message -> handleMessage(sessionId, message))
			.doFinally(signalType -> {
				sessions.remove(sessionId);
				sessions.values().forEach(s -> {
					s.send(Mono.just(s.textMessage("{\"type\":\"leave\",\"id\":\"" + sessionId + "\"}"))).subscribe();
				});
			})
			.then();
	}

	private Mono<Void> handleMessage(String sessionId, String message) {
		Map<String, Object> parsedMessage = parseMessage(message);
		String type = (String) parsedMessage.get("type");
		String targetId = (String) parsedMessage.get("id");

		if (targetId != null && sessions.containsKey(targetId)) {
			WebSocketSession targetSession = sessions.get(targetId);
			switch (type) {
				case "offer":
				case "answer":
				case "ice-candidate":
					return targetSession.send(Mono.just(targetSession.textMessage(message)));
			}
		}
		return Mono.empty();
	}

	private Map<String, Object> parseMessage(String message) {
		try {
			return new ObjectMapper().readValue(message, new TypeReference<Map<String, Object>>() {});
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
