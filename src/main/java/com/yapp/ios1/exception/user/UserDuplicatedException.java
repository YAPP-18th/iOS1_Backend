package com.yapp.ios1.exception.user;

import lombok.Getter;

/**
 * created by ayoung 2021/04/14
 */
@Getter
public class UserDuplicatedException extends RuntimeException {
    public UserDuplicatedException(String message) {
        super(message);
    }
}