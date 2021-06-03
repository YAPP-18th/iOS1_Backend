package com.yapp.ios1.error.exception.user;

import com.yapp.ios1.error.exception.ErrorCode;
import com.yapp.ios1.error.exception.InvalidValueException;

/**
 * created by jg 2021/06/03
 */
public class SocialTypeNotFoundException extends InvalidValueException {
    public SocialTypeNotFoundException() {
        super(ErrorCode.SOCIAL_TYPE_NOT_FOUND);
    }
}
