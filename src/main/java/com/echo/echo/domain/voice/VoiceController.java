package com.echo.echo.domain.voice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.socket.server.WebSocketService;
import org.springframework.web.server.ServerWebExchange;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class VoiceController {

	private final VoiceHandler voiceHandler;
	private final WebSocketService webSocketService;

	@GetMapping("/voice")
	public Mono<Void> handleWebSocketRequest(ServerWebExchange exchange) {
		return webSocketService.handleRequest(exchange, voiceHandler);
	}
}
