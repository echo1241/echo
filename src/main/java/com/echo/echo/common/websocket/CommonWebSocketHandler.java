package com.echo.echo.common.websocket;

import com.echo.echo.domain.user.entity.User;
import org.reactivestreams.Subscription;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

import java.util.Map;

public interface CommonWebSocketHandler {
    Mono<Void> receive(String payload);
    Flux<String> send();
    Mono<Void> sendTyping(String payload);
    void init(WebSocketSession webSocketSession, Map<String, String> queryParams, Mono<User> userMono);
    Mono<Boolean> startSession();
    void doOnSubscribe(Subscription subscription);
    void doFinally(SignalType signal);
    void doOnError(Throwable e);
}
