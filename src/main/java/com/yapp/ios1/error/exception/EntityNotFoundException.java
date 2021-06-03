package com.yapp.ios1.error.exception;

/**
 * created by jg 2021/06/03
 */
public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
