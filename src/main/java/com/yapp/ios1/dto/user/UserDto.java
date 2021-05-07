package com.yapp.ios1.dto.user;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * created by jg 2021/03/28
 */
@AllArgsConstructor
@Getter
@Builder
public class UserDto {
    private Long id;
    private String email;
    private String socialType;
    private String nickname;
    private String password;
    private String intro;
    private String createdDate;
    private String socialId;

    public UserDto(String email, String socialType, String nickname, String password, String intro) {
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
