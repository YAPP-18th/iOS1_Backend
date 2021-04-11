package com.yapp.ios1.utils.auth;

import com.yapp.ios1.dto.JwtPayload;
import com.yapp.ios1.dto.user.UserDto;
import com.yapp.ios1.exception.UserNotFoundException;
import com.yapp.ios1.mapper.UserMapper;
import com.yapp.ios1.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final UserMapper userMapper;
    private final HttpServletRequest httpServletRequest;

    @Around("@annotation(Auth)")
    public Object around(final ProceedingJoinPoint pjp) throws Throwable {
        try {
            String token = httpServletRequest.getHeader(AUTHORIZATION);

            JwtPayload payload = jwtService.getPayload(token);

            UserDto user = userMapper.findByUserId(payload.getId());

            if (user == null) {
                throw new UserNotFoundException("존재하지 않는 유저입니다.");
            }

            return pjp.proceed(pjp.getArgs());
        } catch (SignatureException | ExpiredJwtException | MalformedJwtException | UnsupportedJwtException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}