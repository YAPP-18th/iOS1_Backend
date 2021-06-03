package com.yapp.ios1.error.exception.bucket;

import com.yapp.ios1.error.exception.EntityNotFoundException;
import com.yapp.ios1.error.exception.ErrorCode;

/**
 * created by ayoung 2021/05/21
 */
public class BucketNotFoundException extends EntityNotFoundException {
    public BucketNotFoundException() {
        super(ErrorCode.ENTITY_NOT_FOUND);
    }
}
