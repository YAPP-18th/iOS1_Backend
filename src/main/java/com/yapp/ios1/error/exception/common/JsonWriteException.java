package com.yapp.ios1.error.exception.common;

import com.yapp.ios1.error.exception.BusinessException;
import com.yapp.ios1.error.exception.ErrorCode;

/**
 * created by jg 2021/06/03
 */
public class JsonWriteException extends BusinessException {
    public JsonWriteException() {
        super(ErrorCode.JSON_WRITE_ERROR);
    }
}
