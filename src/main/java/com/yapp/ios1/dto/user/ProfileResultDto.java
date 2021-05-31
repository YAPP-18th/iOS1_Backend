package com.yapp.ios1.dto.user;

import lombok.Getter;

/**
 * created by ayoung 2021/05/18
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
