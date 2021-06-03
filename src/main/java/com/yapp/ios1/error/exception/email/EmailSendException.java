package com.yapp.ios1.error.exception.email;

import com.yapp.ios1.error.exception.BusinessException;
import com.yapp.ios1.error.exception.ErrorCode;

/**
 * created by jg 2021/06/03
 */
public class EmailSendException extends BusinessException {
    public EmailSendException() {
        super(ErrorCode.EMAIL_SEND_ERROR);
    }
}
