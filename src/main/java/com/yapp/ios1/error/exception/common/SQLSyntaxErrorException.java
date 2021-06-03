package com.yapp.ios1.error.exception.common;

/**
 * created by jg 2021/06/03
 */
public class SQLSyntaxErrorException extends InternalServerException {
    public SQLSyntaxErrorException(String message) {
        super(message);
    }
}
