package com.yapp.ios1.controller.dto.user.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static com.yapp.ios1.message.ValidMessage.NOT_VALID_EMAIL;

/**
 * created by ayoung 2021/04/15
 */
@AllArgsConstructor
@Getter
public class SignInDto {
    @NotBlank
    @Email(message = NOT_VALID_EMAIL)
    private String email;
    @NotBlank
    private String password;
}
