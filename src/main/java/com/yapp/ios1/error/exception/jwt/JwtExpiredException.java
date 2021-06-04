package com.yapp.ios1.error.exception.jwt;

import com.yapp.ios1.error.exception.ErrorCode;

/**
 * created by ayoung 2021/06/04
 */
public class JwtExpiredException extends JwtException {
    public JwtExpiredException() {
        super(ErrorCode.JWT_EXPIRED_ERROR);
    }
}
