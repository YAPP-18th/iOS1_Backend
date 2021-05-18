package com.yapp.ios1.dto.user.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * created by ayoung 2021/04/15
 */
@AllArgsConstructor
@Getter
public class SignInDto {
    private String email;
    private String password;
}
