package com.yapp.ios1.exception.bucket;

/**
 * created by ayoung 2021/05/21
 */
public class BucketNotFoundException extends RuntimeException {
    public BucketNotFoundException(String message) {
        super(message);
    }
}
