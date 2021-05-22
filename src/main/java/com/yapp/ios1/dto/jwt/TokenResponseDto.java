package com.yapp.ios1.dto.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * created by ayoung 2021/05/23
 */
@AllArgsConstructor
@Getter
@Builder
public class TokenResponseDto {
    private String accessToken;
    private String refreshToken;
}

