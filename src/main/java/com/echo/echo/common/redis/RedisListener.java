package com.echo.echo.common.redis;

import com.echo.echo.domain.text.controller.TextWebSocketHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisListener {

    private final TextWebSocketHandler textWebSocketHandler;

    // Redis에서 Listen되고 있는 토픽이 추가될 때 case 추가하여 메시징 처리 로직으로 연결
    public Mono<Void> handleMessage(RedisConst topic, String body) {
        switch (topic) {
            case TEXT:
                return textWebSocketHandler.sendText(body).then();
            default:
                return Mono.empty();
        }
    }
}
