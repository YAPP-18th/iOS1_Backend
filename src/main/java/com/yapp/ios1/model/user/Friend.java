package com.yapp.ios1.model.user;

import lombok.Getter;

/**
 * created by ayoung 2021/05/12
 * 친구 목록 조회, 유저 검색 모델
 */
@Getter
public class Friend {
    private Long userId;
    private String email;
    private String nickname;
    private String intro;
    private String profileUrl;
    private int friendStatus;
}
