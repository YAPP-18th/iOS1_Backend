package com.yapp.ios1.dto.user;

import com.yapp.ios1.dto.jwt.TokenResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * created by ayoung 2021/05/05
 * TODO 클래스 이름 변경하면 좋을 거 같습니다.
 * 소셜 로그인 로그인, 회원가입 구분하는 Dto
 */
@AllArgsConstructor
@Getter
public class UserCheckDto {
    private HttpStatus status;
    private TokenResponseDto tokenDto;
}
