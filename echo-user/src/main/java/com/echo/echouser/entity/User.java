package com.echo.echouser.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table
public class User {
    @Id
    private Long id;
    private String username;
    private String password;
    private String email;
    private String intro;
    private String status;

    @Builder
    public User(Long id, String username, String password, String email, String intro, String status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.intro = intro;
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", intro='" + intro + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
