package com.yapp.ios1.dto.notification.response;

import lombok.Getter;

/**
 * created by jg 2021/05/26
 */
@Getter
public class NotificationLogResultDto {
    private Long alarmId;
    private Long myUserId;
    private Long friendId;
    private String title;
    private String message;
    private Integer friendStatus;
    private String createdAt;
}
