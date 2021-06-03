package com.yapp.ios1.error.exception.common;

import com.yapp.ios1.error.exception.BusinessException;

/**
 * created by jg 2021/06/03
 */
public class InternalServerException extends BusinessException {

    public InternalServerException(String message) {
        super(message);
    }
}
