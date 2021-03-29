package com.yapp.ios1.dto.user;

import lombok.Getter;

/**
 * created by jg 2021/03/28
 */
@Getter
public class UserDto {
    // 나중에 수정 예정 (지금은 임시)
    private Long id;
    private String email;
    private String socialType;

    public UserDto(String email, String socialType) {
        this.email = email;
        this.socialType = socialType;
    }
}
