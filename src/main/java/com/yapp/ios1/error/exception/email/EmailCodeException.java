package com.yapp.ios1.error.exception.email;

import com.yapp.ios1.error.exception.ErrorCode;
import com.yapp.ios1.error.exception.InvalidValueException;

/**
 * created by ayoung 2021/06/15
 */
public class EmailCodeException extends InvalidValueException {
    public EmailCodeException() {
        super(ErrorCode.EMAIL_CODE_ERROR);
    }
}