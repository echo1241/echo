package com.echo.echo.common.websocket;

import com.echo.echo.domain.user.entity.User;
import lombok.Getter;
import org.reactivestreams.Subscription;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

import java.util.Map;

@Getter
public abstract class AbstractWebSocketHandler implements CommonWebSocketHandler {
    private WebSocketSession webSocketSession;
    private Map<String, String> queryParams;
    private Mono<User> userMono;

    @Override
    public void init(WebSocketSession webSocketSession, Map<String, String> queryParams, Mono<User> userMono) {
        this.webSocketSession = webSocketSession;
        this.queryParams = queryParams;
        this.userMono = userMono;
    }

    @Override
    public Mono<Void> receive(String payload) {
        return null;
    }

    @Override
    public Flux<String> send() {
        return null;
    }

    @Override
    public Mono<Void> sendTyping(String payload) {return Mono.empty();};


    @Override
    public void doOnSubscribe(Subscription subscription) {

    }

    @Override
    public void doFinally(SignalType signal) {

    }

    @Override
    public void doOnError(Throwable e) {

    }
}
