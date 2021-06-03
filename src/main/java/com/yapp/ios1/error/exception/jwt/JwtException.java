package com.yapp.ios1.error.exception.jwt;

import com.yapp.ios1.error.exception.BusinessException;
import com.yapp.ios1.error.exception.ErrorCode;

/**
 * created by jg 2021/06/03
 */
public class JwtException extends BusinessException {
    public JwtException() {
        super(ErrorCode.JWT_ERROR);
    }
}
