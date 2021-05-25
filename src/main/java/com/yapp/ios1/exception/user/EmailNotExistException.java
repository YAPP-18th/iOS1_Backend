package com.yapp.ios1.exception.user;

import lombok.Getter;

/**
 * created by ayoung 2021/05/25
 */
@Getter
public class EmailNotExistException extends RuntimeException {
    public EmailNotExistException(String message) {
        super(message);
    }
}
