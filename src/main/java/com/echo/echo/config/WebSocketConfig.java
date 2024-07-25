package com.echo.echo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.socket.server.WebSocketService;
import org.springframework.web.reactive.socket.server.support.HandshakeWebSocketService;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

@Configuration
@EnableWebFlux
public class WebSocketConfig implements WebFluxConfigurer {

	@Bean
	public WebSocketHandlerAdapter webSocketHandlerAdapter() {
		return new WebSocketHandlerAdapter(webSocketService());
	}

	@Bean
	public WebSocketService webSocketService() {
		return new HandshakeWebSocketService();
	}
}