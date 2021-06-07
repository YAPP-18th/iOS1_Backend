package com.yapp.ios1.error.exception.alarm;

import com.yapp.ios1.error.exception.ErrorCode;
import com.yapp.ios1.error.exception.InvalidValueException;

/**
 * created by jg 2021/06/07
 */
public class AlarmNotFoundException extends InvalidValueException {
    public AlarmNotFoundException() {
        super(ErrorCode.ALARM_NOT_FOUND_ERROR);
    }
}
