package com.yapp.ios1.controller.dto.user.login;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

/**
 * created by ayoung 2021/05/30
 */
@Getter
public class PasswordDto {
    @NotBlank
    private String password;
}
