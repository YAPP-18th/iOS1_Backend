package com.yapp.ios1.error.exception.common;

import com.yapp.ios1.error.exception.BusinessException;

/**
 * created by ayoung 2021/05/30
 */
public class BadRequestException extends BusinessException {
    public BadRequestException(String message) {
        super(message);
    }
}
