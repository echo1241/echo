package com.echo.echo.domain.auth;

import com.echo.echo.domain.auth.dto.LoginResponseDto;
import com.echo.echo.domain.user.entity.User;
import com.echo.echo.security.jwt.JwtProvider;
import com.echo.echo.security.jwt.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public Mono<LoginResponseDto> login(String inputPassword, User user) {
        return checkPassword(inputPassword, user.getPassword())
                .then(Mono.defer(() -> jwtProvider.createToken(user.getId(), user.getEmail())))
                .map(LoginResponseDto::new);
    }

    private Mono<Void> checkPassword(String inputPassword, String password) {
        return Mono.just(passwordEncoder.matches(inputPassword, password))
                .filter(isMatch -> isMatch)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("비밀번호가 일치하지 않습니다.")))
                .then();
    }
}