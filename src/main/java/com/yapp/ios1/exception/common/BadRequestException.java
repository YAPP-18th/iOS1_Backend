package com.yapp.ios1.exception.common;

/**
 * created by ayoung 2021/05/30
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
