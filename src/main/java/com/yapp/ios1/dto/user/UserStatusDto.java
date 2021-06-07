package com.yapp.ios1.dto.user;

import com.yapp.ios1.dto.jwt.TokenResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * created by ayoung 2021/05/05
 * 소셜 로그인 로그인, 회원가입 구분하는 Dto
 */
@AllArgsConstructor
@Getter
public class UserStatusDto {
    private HttpStatus status;
    private TokenResponseDto tokenDto;
}
