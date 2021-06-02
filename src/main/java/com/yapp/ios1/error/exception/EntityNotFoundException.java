package com.yapp.ios1.error.exception;

/**
 * created by jg 2021/06/03
 */
public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }
}
