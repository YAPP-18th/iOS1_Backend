package com.yapp.ios1.dto.notification;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * created by jg 2021/05/21
 */
@Getter
public class NotificationForOneDto extends NotificationDto {

    private String deviceToken;

    @Builder
    public NotificationForOneDto(String title, String message, LocalDateTime localDateTime, String deviceToken) {
        super(title, message, localDateTime);
        this.deviceToken = deviceToken;
    }
}
