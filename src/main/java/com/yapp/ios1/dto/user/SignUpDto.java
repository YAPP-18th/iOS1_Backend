package com.yapp.ios1.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * created by ayoung 2021/04/14
 */
@AllArgsConstructor
@Getter
public class SignUpDto {
    private String email;
    private String password;
    private String nickname;
    private String intro;

    public void setPassword(String password) {
        this.password = password;
    }
}
