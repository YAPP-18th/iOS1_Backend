package com.yapp.ios1.aop;

import com.yapp.ios1.dto.jwt.JwtPayload;
import com.yapp.ios1.model.user.User;
import com.yapp.ios1.error.exception.jwt.JwtException;
import com.yapp.ios1.error.exception.jwt.JwtExpiredException;
import com.yapp.ios1.service.jwt.JwtIssueService;
import com.yapp.ios1.service.jwt.JwtService;
import com.yapp.ios1.service.user.UserFindService;
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

    private static final String AUTHORIZATION = "accessToken";
    private static final String REAUTHORIZATION = "refreshToken";

    private final JwtService jwtService;
    private final JwtIssueService jwtIssueService;
    private final UserFindService userFindService;
    private final HttpServletRequest httpServletRequest;

    @Around("@annotation(com.yapp.ios1.annotation.Auth)")
    public Object accessToken(final ProceedingJoinPoint pjp) throws Throwable {
        try {
            String accessToken = httpServletRequest.getHeader(AUTHORIZATION);
            JwtPayload payload = jwtService.getPayload(accessToken);
            User user = userFindService.getUser(payload.getId());
            UserContext.USER_CONTEXT.set(new JwtPayload(user.getId()));
            return pjp.proceed();
        } catch (SignatureException | ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new JwtException();
        }
    }

    @Around("@annotation(com.yapp.ios1.annotation.ReAuth)")
    public Object refreshToken(final ProceedingJoinPoint pjp) throws Throwable {
        try {
            String refreshToken = httpServletRequest.getHeader(REAUTHORIZATION);
            JwtPayload payload = jwtService.getPayload(refreshToken);
            User user = userFindService.getUser(payload.getId());

            String dbRefreshToken = jwtIssueService.getRefreshTokenByUserId(user.getId());
            checkRefreshTokenExpired(dbRefreshToken, refreshToken);
            UserContext.USER_CONTEXT.set(new JwtPayload(user.getId()));
            return pjp.proceed();
        } catch (SignatureException | ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new JwtException();
        }
    }

    private void checkRefreshTokenExpired(String dbRefreshToken, String refreshToken) {
        if (!dbRefreshToken.equals(refreshToken)) {
            throw new JwtExpiredException();
        }
    }
}