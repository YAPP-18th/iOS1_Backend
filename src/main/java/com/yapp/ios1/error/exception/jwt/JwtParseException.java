package com.yapp.ios1.error.exception.jwt;

import com.yapp.ios1.error.exception.ErrorCode;

/**
 * created by jg 2021/06/03
 */
public class JwtParseException extends JwtException {
    public JwtParseException() {
        super(ErrorCode.JWT_PARSE_ERROR);
    }
}
