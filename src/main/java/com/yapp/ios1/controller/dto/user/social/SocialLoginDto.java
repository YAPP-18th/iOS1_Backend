package com.yapp.ios1.controller.dto.user.social;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * created by ayoung 2021/05/25
 */
@Getter
public class SocialLoginDto {
    @NotNull
    @Email
    private String email;
    @NotBlank
    private String socialId;
    @NotBlank
    private String deviceToken;
}
