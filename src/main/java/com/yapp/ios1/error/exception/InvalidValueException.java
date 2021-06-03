package com.yapp.ios1.error.exception;

/**
 * created by jg 2021/06/03
 */
public class InvalidValueException extends BusinessException {

    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }
}
