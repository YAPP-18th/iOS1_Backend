package com.yapp.ios1.error.exception.aws;

import com.yapp.ios1.error.exception.BusinessException;
import com.yapp.ios1.error.exception.ErrorCode;

/**
 * created by jg 2021/06/04
 */
public class S3FileIOException extends BusinessException {
    public S3FileIOException() {
        super(ErrorCode.AWS_S3_IO_ERROR);
    }
}
