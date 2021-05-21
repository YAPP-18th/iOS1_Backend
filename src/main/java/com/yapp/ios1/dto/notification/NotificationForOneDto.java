package com.yapp.ios1.dto.notification;

import lombok.Builder;
import lombok.Getter;

/**
 * created by jg 2021/05/21
 */
@Getter
public class NotificationForOneDto extends NotificationDto {

    private String deviceToken;

    @Builder
    public NotificationForOneDto(String title, String message, String deviceToken) {
        super(title, message);
        this.deviceToken = deviceToken;
    }
}
