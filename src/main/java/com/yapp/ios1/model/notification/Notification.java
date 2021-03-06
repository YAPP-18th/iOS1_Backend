package com.yapp.ios1.model.notification;

import lombok.Getter;

/**
 * created by jg 2021/05/26
 * 알림 로그 모델
 */
@Getter
public class Notification {
    private Long alarmId;
    private Long friendId;
    private String title;
    private String message;
    private String nickName;
    private String profileUrl;
    private int friendStatus;
    private int alarmStatus;
    private String createdAt;
}
