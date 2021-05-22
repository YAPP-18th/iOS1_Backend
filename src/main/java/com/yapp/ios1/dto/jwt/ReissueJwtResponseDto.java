package com.yapp.ios1.dto.jwt;

import lombok.Getter;

@Getter
public class ReissueJwtResponseDto {
    private String accessToken;
    private String refreshToken;
}

