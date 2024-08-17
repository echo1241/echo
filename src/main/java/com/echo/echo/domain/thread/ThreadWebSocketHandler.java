package com.echo.echo.domain.thread;

import com.echo.echo.common.util.ObjectStringConverter;
import com.echo.echo.common.websocket.CommonWebSocketHandler;
import com.echo.echo.domain.thread.dto.ThreadMessageRequestDto;
import com.echo.echo.domain.thread.service.ThreadWebSocketService;
import com.echo.echo.domain.user.entity.User;
import com.echo.echo.security.jwt.JwtProvider;
import com.echo.echo.security.principal.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ThreadWebSocketHandler implements CommonWebSocketHandler {

    private final ThreadFacade threadFacade;
    private final ThreadWebSocketService threadWebsocketService;
    private final ObjectStringConverter objectStringConverter;

    @Override
    public Mono<Void> receive(Mono<User> user, Map<String, String> queryParams, String payload) {
        Long spaceId = Long.valueOf(queryParams.get("spaceId"));
        Long threadId = Long.valueOf(queryParams.get("threadId"));
        return objectStringConverter.stringToObject(payload, ThreadMessageRequestDto.class)
                .flatMap(req -> user
                        .flatMap(u -> threadFacade.saveThreadMessage(spaceId, u, threadId, req))
                )
                .flatMap(threadWebsocketService::publishMessage)
                .then();
    }

    @Override
    public Flux<String> send(Map<String, String> queryParams) {
        Long threadId = Long.valueOf(queryParams.get("threadId"));
        return threadWebsocketService.sendMessage(threadId);
    }
}
