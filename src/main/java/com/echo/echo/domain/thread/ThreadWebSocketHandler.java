package com.echo.echo.domain.thread;

import com.echo.echo.common.util.ObjectStringConverter;
import com.echo.echo.common.websocket.AbstractWebSocketHandler;
import com.echo.echo.domain.thread.dto.ThreadMessageRequestDto;
import com.echo.echo.domain.thread.service.ThreadWebSocketService;
import com.echo.echo.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ThreadWebSocketHandler extends AbstractWebSocketHandler {

    private final ThreadFacade threadFacade;
    private final ThreadWebSocketService threadWebsocketService;
    private final ObjectStringConverter objectStringConverter;

    Long threadId;
    Long spaceId;

    @Override
    public void init(WebSocketSession webSocketSession, Map<String, String> queryParams, Mono<User> userMono) {
        super.init(webSocketSession, queryParams, userMono);
        this.threadId = Long.valueOf(queryParams.get("threadId"));
        this.spaceId = Long.valueOf(queryParams.get("spaceId"));
    }

    @Override
    public Mono<Void> receive(String payload) {
        Mono<User> userMono = this.getUserMono();
        return objectStringConverter.stringToObject(payload, ThreadMessageRequestDto.class)
                .flatMap(req -> userMono
                        .flatMap(u -> threadFacade.saveThreadMessage(spaceId, u, threadId, req))
                )
                .flatMap(threadWebsocketService::publishMessage)
                .then();
    }

    @Override
    public Flux<String> send() {
        return threadWebsocketService.sendMessage(threadId);
    }
}
