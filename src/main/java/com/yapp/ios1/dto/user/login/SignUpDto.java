package com.yapp.ios1.dto.user.login;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yapp.ios1.dto.user.login.social.SocialType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static com.yapp.ios1.common.ResponseMessage.NOT_VALID_EMAIL;

/**
 * created by ayoung 2021/04/14
 */
@AllArgsConstructor
@Builder
@Getter
public class SignUpDto {
    @Email(message = NOT_VALID_EMAIL)
    private String email;
    @JsonIgnore
    private SocialType socialType;
    @NotBlank
    private String password;
    @NotBlank
    private String nickname;
    private String intro;
    @JsonIgnore
    private String socialId;
    @NotBlank
    private String deviceToken;
}
