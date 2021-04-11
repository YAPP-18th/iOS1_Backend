package com.yapp.ios1.utils.auth;

import com.yapp.ios1.dto.JwtPayload;
import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * created by jg 2021/04/11
 */
@RequiredArgsConstructor
@Aspect
@Component
public class AuthAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String AUTHORIZATION = "token";

    private final JwtService jwtService;
    private final HttpServletRequest httpServletRequest;

    @Around("@annotation(com.yapp.ios1.utils.auth.Auth)")
    public Object around(final ProceedingJoinPoint pjp) throws Throwable {
        String token = httpServletRequest.getHeader(AUTHORIZATION);

        // Token 이 없을 때 401 에러 반환 하는 코드
        if (token == null) {
            throw new IllegalArgumentException("토큰이 존재하지 않습니다.");
        }

        //토큰 해독
        JwtPayload payload = jwtService.getPayload(token);

        // findByUserId 만들어서 조회한 후에 검증 로직 추가 해야 할 거 같은 ?? (없으면 또 다른 예외 처리를..)

        return pjp.proceed(pjp.getArgs());
    }
}