package com.yapp.ios1.error.exception.bucket;

import com.yapp.ios1.error.exception.EntityNotFoundException;

/**
 * created by ayoung 2021/05/21
 */
public class BucketNotFoundException extends EntityNotFoundException {
    public BucketNotFoundException(String message) {
        super(message);
    }
}
