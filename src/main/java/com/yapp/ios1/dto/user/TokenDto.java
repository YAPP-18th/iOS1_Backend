package com.yapp.ios1.dto.user;

import lombok.Getter;

/**
 * created by jg 2021/04/11
 */
@Getter
public class TokenDto {
    private String accessToken;

    public TokenDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
