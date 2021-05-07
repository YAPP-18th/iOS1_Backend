package com.yapp.ios1.dto.user;

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
    private String socialType;
    private String password;
    private String nickname;
    private String intro;
    private String socialId;
}
