package com.yapp.ios1.error.exception.bucket;

import com.yapp.ios1.error.exception.ErrorCode;
import com.yapp.ios1.error.exception.InvalidValueException;

/**
 * created by jg 2021/06/07
 */
public class bucketStateIdInvalidException extends InvalidValueException {
    public bucketStateIdInvalidException() {
        super(ErrorCode.INVALID_INPUT_VALUE);
    }
}
