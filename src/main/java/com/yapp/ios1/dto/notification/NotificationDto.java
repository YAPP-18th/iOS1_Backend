package com.yapp.ios1.dto.notification;

import lombok.Getter;

/**
 * created by jg 2021/05/17
 */
@Getter
public class NotificationDto {
    private String title;
    private String message;

    public NotificationDto(String title, String message) {
        this.title = title;
        this.message = message;
    }
}

