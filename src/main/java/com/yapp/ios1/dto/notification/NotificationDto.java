package com.yapp.ios1.dto.notification;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * created by jg 2021/05/17
 */
@Getter
public class NotificationDto {
    private String title;
    private String message;
    private LocalDateTime localDateTime;

    @Builder
    public NotificationDto(String title, String message, LocalDateTime localDateTime) {
        this.title = title;
        this.message = message;
        this.localDateTime = localDateTime;
    }
}

