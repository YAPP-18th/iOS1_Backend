package com.yapp.ios1.exception;

/**
 * created by jg 2021/04/11
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
