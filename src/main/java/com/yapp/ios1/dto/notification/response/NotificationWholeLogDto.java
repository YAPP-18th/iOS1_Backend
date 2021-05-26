package com.yapp.ios1.dto.notification.response;

import lombok.Getter;

/**
 * created by jg 2021/05/26
 */
@Getter
public class NotificationWholeLogDto {
    private Long alarmId;
    private String title;
    private String message;
    private String createdAt;
}
