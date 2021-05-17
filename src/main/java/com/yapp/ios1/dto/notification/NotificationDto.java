package com.yapp.ios1.dto.notification;

import lombok.Builder;
import lombok.Getter;

/**
 * created by jg 2021/05/17
 */
@Getter
@Builder
public class NotificationDto {
    private final String title;
    private final String message;

    public static NotificationDto create(String title, String message) {
        return NotificationDto.builder()
                .title(title)
                .message(message)
                .build();
    }
}

