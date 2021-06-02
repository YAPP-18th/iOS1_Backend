package com.yapp.ios1.error.exception.user;

import com.yapp.ios1.error.exception.InvalidValueException;

/**
 * created by ayoung 2021/04/15
 */
public class PasswordNotMatchException extends InvalidValueException {
    public PasswordNotMatchException(String message) {
        super(message);
    }
}
