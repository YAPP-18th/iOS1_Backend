package com.yapp.ios1.utils;

import com.yapp.ios1.dto.notification.NotificationForOneDto;
import com.yapp.ios1.service.user.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * created by jg 2021/06/12
 */
@RequiredArgsConstructor
@Component
public class AlarmMessageUtil {

    private final UserFindService userFindService;

    public NotificationForOneDto createFollowAlarmMessage(String title, String message, Long sendUserId) {
        String deviceToken = userFindService.getDeviceToken(sendUserId);
        return NotificationForOneDto.builder()
                .title(title)
                .message(message)
                .deviceToken(deviceToken)
                .build();
    }
}
