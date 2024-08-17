package com.echo.echo.common.websocket;

import com.echo.echo.domain.user.entity.User;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

import java.util.Map;

public interface CommonWebSocketHandler {
    Mono<Void> receive(Mono<User> user, Map<String, String> queryParams, String payload);
    Flux<String> send(Map<String, String> queryParams);
    default Mono<Void> sendTyping() {return Mono.empty();};
    default void init() {};
    default void doOnSubscribe(Subscription subscription) {};
    default void doFinally(SignalType signal) {};
    default void doOnError(Throwable e) {};
}
