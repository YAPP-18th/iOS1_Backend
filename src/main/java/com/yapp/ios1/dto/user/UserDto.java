package com.yapp.ios1.dto.user;

import lombok.Getter;

/**
 * created by jg 2021/03/28
 */
@Getter
public class UserDto {
    private Long id;
    private String email;
    private String socialType;
    private String nickname;
    private String password;
    private String intro;

    public UserDto(String email, String socialType, String nickname, String password, String intro) {
        this.email = email;
        this.socialType = socialType;
        this.nickname = nickname;
        this.password = password;
        this.intro = intro;
    }

    public static UserDto of(SignUpDto signUpDto) {
        return new UserDto(signUpDto.getEmail(), "", signUpDto.getNickname(), signUpDto.getPassword(), signUpDto.getIntro());
    }
}
