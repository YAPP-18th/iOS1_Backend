package com.yapp.ios1.dto.notification;

import lombok.Getter;

/**
 * created by jg 2021/05/26
 */
@Getter
public class DeviceTokenDto {
    private Long userId;
    private String deviceToken;

    public DeviceTokenDto(Long userId, String deviceToken) {
        this.userId = userId;
        this.deviceToken = deviceToken;
    }
}
