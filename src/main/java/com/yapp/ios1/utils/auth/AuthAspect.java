package com.yapp.ios1.utils.auth;

import com.yapp.ios1.dto.JwtPayload;
import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
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
import java.security.SignatureException;

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

    @Around("@annotation(Auth)")
    public Object around(final ProceedingJoinPoint pjp) throws Throwable {
        try {
            String token = httpServletRequest.getHeader(AUTHORIZATION);

            JwtPayload payload = jwtService.getPayload(token);

            // findByUserId 만들어서 조회한 후에 검증 로직 추가하기

            return pjp.proceed(pjp.getArgs());
        } catch (SignatureException | ExpiredJwtException | MalformedJwtException | UnsupportedJwtException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}