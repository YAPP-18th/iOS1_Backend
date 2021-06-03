package com.yapp.ios1.error.exception.infra;

import com.yapp.ios1.error.exception.common.InternalServerException;

/**
 * created by jg 2021/06/03
 */
public class MailSendException extends InternalServerException {
    public MailSendException(String message) {
        super(message);
    }
}
