package com.yapp.ios1.exception;

/**
 * created by ayoung 2021/04/15
 */
public class PasswordNotMatchException extends RuntimeException {
    public PasswordNotMatchException(String message) {
        super(message);
    }
}
