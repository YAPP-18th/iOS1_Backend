package com.yapp.ios1.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yapp.ios1.controller.dto.user.login.SignUpDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * created by jg 2021/03/28
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class User {
    private Long id;
    private String email;
    @JsonIgnore
    private String socialType;
    private String nickname;
    @JsonIgnore
    private String password;
    private String intro;
    private String createdDate;
    @JsonIgnore
    private String socialId;
    @JsonIgnore
    private String deviceToken;
    private String profileUrl;
    private boolean isAlarmCheck;

    public static User of(SignUpDto signUpDto) {
        return User.builder()
                .email(signUpDto.getEmail())
                .nickname(signUpDto.getNickname())
                .password(signUpDto.getPassword())
                .intro(signUpDto.getIntro())
                .socialType(signUpDto.getSocialType())
                .socialId(signUpDto.getSocialId())
                .deviceToken(signUpDto.getDeviceToken())
                .build();
    }

    public void encodePassword(String password) {
        this.password = password;
    }
}
