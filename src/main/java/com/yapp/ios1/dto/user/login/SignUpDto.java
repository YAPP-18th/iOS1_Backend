package com.yapp.ios1.dto.user.login;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yapp.ios1.dto.user.login.social.SocialType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * created by ayoung 2021/04/14
 */
@AllArgsConstructor
@Builder
@Getter
public class SignUpDto {
    private String email;
    @JsonIgnore
    private SocialType socialType;
    private String password;
    private String nickname;
    private String intro;
    @JsonIgnore
    private String socialId;
}
