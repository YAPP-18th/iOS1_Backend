package com.yapp.ios1.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * created by jg 2021/04/11
 */
@NoArgsConstructor
@Getter
public class TokenDto {
    private String accessToken;

    public TokenDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
