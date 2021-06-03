package com.yapp.ios1.error.exception.user;

import com.yapp.ios1.error.exception.EntityNotFoundException;

/**
 * created by jg 2021/04/11
 */
public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
