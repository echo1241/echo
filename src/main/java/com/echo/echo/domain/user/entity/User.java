package com.echo.echo.domain.user.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {
    @Id
    private Long id;
    private String email;
    private String password;
    private String intro;
    private int status;
    private int verificationCode;

    // 상태: 인증 전, 인증 완료, 탈퇴
    public enum Status {
        TEMPORARY, ACTIVATE, DEACTIVATE
    }

    @Builder
    public User(Long id, String email, String password, String intro, Status status, Integer verificationCode) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.intro = intro;
        this.status = status == null? Status.TEMPORARY.ordinal() : status.ordinal();
        this.verificationCode = verificationCode == null? new Random(System.currentTimeMillis()).nextInt(900000) + 100000 : verificationCode;
    }

    public void activateStatus() {
        if (this.status == Status.ACTIVATE.ordinal()) {
            throw new RuntimeException("이미 활성화된 계정입니다.");
        }
        this.status = Status.ACTIVATE.ordinal();
    }

    public boolean checkVerificationCode(int inputCode) {
        return this.getVerificationCode() == inputCode;
    }
}
