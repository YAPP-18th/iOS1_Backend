package com.yapp.ios1.utils.auth;

import com.yapp.ios1.common.ResponseMessage;
import com.yapp.ios1.dto.jwt.JwtPayload;
import com.yapp.ios1.dto.user.UserDto;
import com.yapp.ios1.exception.user.UserNotFoundException;
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
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * created by jg 2021/04/11
 */
@RequiredArgsConstructor
@Aspect
@Component
public class AuthAspect {

    private static final String AUTHORIZATION = "token";

    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final HttpServletRequest httpServletRequest;

    @Around("@annotation(Auth)")
    public Object around(final ProceedingJoinPoint pjp) throws Throwable {
        try {
            String token = httpServletRequest.getHeader(AUTHORIZATION);

            JwtPayload payload = jwtService.getPayload(token);

            UserDto user = userMapper.findByUserId(payload.getId())
                    .orElseThrow(() -> new UserNotFoundException(ResponseMessage.NOT_FOUND_USER));

            UserContext.USER_CONTEXT.set(new JwtPayload(user.getId()));

            return pjp.proceed();
        } catch (SignatureException | ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}