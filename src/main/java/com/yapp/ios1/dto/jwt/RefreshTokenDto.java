package com.yapp.ios1.dto.jwt;

import lombok.Getter;

/**
 * created by ayoung 2021/06/03
 */
@Getter
public class RefreshTokenDto {
    private Long userId;
    private String refreshToken;
}
