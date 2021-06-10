package com.yapp.ios1.controller.dto.user.login;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.yapp.ios1.message.ValidMessage.NOT_VALID_EMAIL;

/**
 * created by ayoung 2021/04/14
 */
@AllArgsConstructor
@Builder
@Getter
public class SignUpDto {
    @NotBlank
    @Email(message = NOT_VALID_EMAIL)
    private String email;
    @JsonIgnore
    private String socialType;
    @NotBlank
    private String password;
    @NotBlank
    private String nickname;
    @NotNull
    private String intro;
    @JsonIgnore
    private String socialId;
    @NotBlank
    private String deviceToken;
}
