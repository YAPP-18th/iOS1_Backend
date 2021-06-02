package com.yapp.ios1.error.exception.user;

import com.yapp.ios1.error.exception.InvalidValueException;
import lombok.Getter;

/**
 * created by ayoung 2021/04/14
 */
@Getter
public class UserDuplicatedException extends InvalidValueException {
    public UserDuplicatedException(String message) {
        super(message);
    }
}