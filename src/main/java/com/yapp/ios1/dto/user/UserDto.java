package com.yapp.ios1.dto.user;

import lombok.Builder;
import lombok.Getter;

/**
 * created by jg 2021/03/28
 */
@Getter
@Builder
public class UserDto {
    private Long id;
    private String email;
    private String socialType;
    private String nickname;
    private String password;
    private String intro;

    public static UserDto of(SignUpDto signUpDto) {
        return UserDto.builder()
                .email(signUpDto.getEmail())
                .nickname(signUpDto.getNickname())
                .password(signUpDto.getPassword())
                .intro(signUpDto.getIntro())
                .build();
    }
}
