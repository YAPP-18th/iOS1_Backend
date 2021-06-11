package com.yapp.ios1.aop;

import com.yapp.ios1.dto.jwt.JwtPayload;
import com.yapp.ios1.error.exception.jwt.JwtException;

/**
 * created by ayoung 2021/05/01
 */
public class UserContext {

    public static final ThreadLocal<JwtPayload> USER_CONTEXT = new ThreadLocal<>();

    public static Long getCurrentUserId() {
        if (UserContext.USER_CONTEXT.get() != null) {
            return UserContext.USER_CONTEXT.get().getId();
        }

        throw new JwtException();
    }
}