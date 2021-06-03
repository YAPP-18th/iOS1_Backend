package com.yapp.ios1.error.exception.common;

import com.yapp.ios1.error.exception.ErrorCode;
import com.yapp.ios1.error.exception.InvalidValueException;

/**
 * created by jg 2021/06/03
 */
public class DateTimeParseException extends InvalidValueException {

    public DateTimeParseException() {
        super(ErrorCode.INVALID_INPUT_VALUE);
    }
}
