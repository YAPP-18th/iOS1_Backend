package com.yapp.ios1.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yapp.ios1.dto.user.social.SocialType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * created by jg 2021/03/28
 */
@AllArgsConstructor
@Getter
@Builder
public class UserDto {
    private Long id;
    private String email;
    @JsonIgnore
    private SocialType socialType;
    private String nickname;
    @JsonIgnore
    private String password;
    private String intro;
    private String createdDate;
    @JsonIgnore
    private String socialId;

    public UserDto(String email, SocialType socialType, String nickname, String password, String intro) {
        this.email = email;
        this.socialType = socialType;
        this.nickname = nickname;
        this.password = password;
        this.intro = intro;
    }

    public static UserDto of(SignUpDto signUpDto) {
        return UserDto.builder()
                .email(signUpDto.getEmail())
                .nickname(signUpDto.getNickname())
                .password(signUpDto.getPassword())
                .intro(signUpDto.getIntro())
                .socialType(signUpDto.getSocialType())
                .socialId(signUpDto.getSocialId())
                .build();
    }

    public void encodePassword(String password) {
        this.password = password;
    }
}
