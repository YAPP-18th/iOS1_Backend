package com.yapp.ios1.model.user;

import lombok.Getter;

/**
 * created by ayoung 2021/05/18
 * 디바이스 토큰, 패스워드 제외한 프로필 모델
 */
@Getter
public class Profile {
    private Long id;
    private String email;
    private String nickname;
    private String createdDate;
    private String intro;
    private String profileUrl;
    private String socialType;
    private String socialId;
}
