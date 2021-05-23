package com.yapp.ios1.dto.jwt;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

/**
 * created by ayoung 2021/05/23
 */
@AllArgsConstructor
@Getter
@Builder
public class TokenResponseDto {
    private String accessToken;
    private String refreshToken;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date accessExpiredAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date refreshExpiredAt;
}

