package com.yapp.ios1.dto.search;

import lombok.Getter;

/**
 * created by jg 2021/05/18
 */
@Getter
public class UserSearchDto {
    private Long userId;
    private String nickName;
    private String intro;
    private boolean isFriend;
}
