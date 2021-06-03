package com.yapp.ios1.error.exception.infra;

import com.yapp.ios1.error.exception.common.InternalServerException;

/**
 * created by jg 2021/05/17
 */
public class FirebaseInitException extends InternalServerException {
    public FirebaseInitException(String message) {
        super(message);
    }
}
