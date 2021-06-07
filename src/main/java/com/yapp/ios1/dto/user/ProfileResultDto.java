package com.yapp.ios1.dto.user;

import lombok.Getter;

/**
 * created by ayoung 2021/05/18
 * TODO 클래스 이름 변경하기 + Model 로 이동?
 */
@Getter
public class ProfileResultDto {
    private Long id;
    private String email;
    private String nickname;
    private String createdDate;
    private String intro;
    private String profileUrl;
    private String socialType;
    private String socialId;
}
