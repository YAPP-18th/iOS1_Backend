package com.yapp.ios1.dto.notification.response;

import lombok.Getter;
import lombok.ToString;

/**
 * created by jg 2021/05/26
 */
@ToString
@Getter
public class NotificationFollowLogDto {
    private Long myUserId;
    private Long friendId;
    private String message;
    private Integer friendStatus;
    private String createdAt;
}
