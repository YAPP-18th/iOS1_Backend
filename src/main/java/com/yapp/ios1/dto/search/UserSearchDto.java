package com.yapp.ios1.dto.search;

import lombok.Getter;

/**
 * created by jg 2021/05/18
 * 분리하기
 */
@Getter
public class UserSearchDto {
    private Long userId;
    private String nickName;
    private String intro;
    private String profileUrl;
    private Integer friendStatus = 3;
}
