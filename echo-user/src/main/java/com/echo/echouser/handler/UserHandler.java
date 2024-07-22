package com.echo.echouser.handler;

import com.echo.echouser.dto.UserDto;
import com.echo.echouser.entity.User;
import com.echo.echouser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

    private final UserRepository userRepository;

    public UserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<ServerResponse> createUser(ServerRequest request){
        return request.bodyToMono(UserDto.class)
                .map(dto -> User.builder()
                        .username(dto.getUsername())
                        .password(dto.getPassword())
                        .email(dto.getEmail())
                        .intro(dto.getIntro())
                        .build())
                .flatMap(userRepository::save)
                .map(UserDto::new)
                .flatMap(user-> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(user));

    }

    public Mono<ServerResponse> getUser(ServerRequest request){
        Long id = Long.valueOf(request.pathVariable("userId"));
        return userRepository.findById(id)
                .map(UserDto::new)
                .flatMap(res -> ServerResponse.ok().bodyValue(res));
    }

}
