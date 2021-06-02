package com.yapp.ios1.error.exception.user;

import com.yapp.ios1.error.exception.InvalidValueException;
import lombok.Getter;

/**
 * created by ayoung 2021/05/25
 */
@Getter
public class EmailNotExistException extends InvalidValueException {
    public EmailNotExistException(String message) {
        super(message);
    }
}
