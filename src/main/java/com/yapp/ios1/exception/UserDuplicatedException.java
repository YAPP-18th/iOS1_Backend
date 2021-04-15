package com.yapp.ios1.exception;

import lombok.Getter;

/**
 * created by ayoung 2021/04/14
 */
@Getter
public class UserDuplicatedException extends RuntimeException {

    private final String email;

    public UserDuplicatedException(String email) {
        this.email = email;
    }

}