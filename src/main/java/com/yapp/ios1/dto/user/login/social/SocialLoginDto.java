package com.yapp.ios1.dto.user.login.social;

import lombok.Getter;

import javax.validation.constraints.Email;

/**
 * created by ayoung 2021/05/25
 */
@Getter
public class SocialLoginDto {
    @Email
    private String email;
    private String token;
}
