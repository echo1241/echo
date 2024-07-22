package com.echo.echouser.router;

import com.echo.echouser.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

@Configuration("userRouter")
public class UserRouter {

    @Bean
    public RouterFunction<?> routeUser(UserHandler handler) {
        return RouterFunctions.route()
                .POST("/users", handler::createUser)
                .GET("/users/{userId}", handler::getUser)
                .build();
    }
}
