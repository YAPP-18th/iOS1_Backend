package com.yapp.ios1.utils;

import com.yapp.ios1.dto.notification.NotificationForOneDto;
import com.yapp.ios1.service.user.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.yapp.ios1.message.AlarmMessage.FOLLOW_ACCEPT_MESSAGE;
import static com.yapp.ios1.message.AlarmMessage.FOLLOW_ACCEPT_TITLE;

/**
 * created by jg 2021/06/12
 */
@RequiredArgsConstructor
@Component
public class AlarmMessageUtil {

    private final UserFindService userFindService;

    public NotificationForOneDto createFollowAlarmMessage(Long sendUserId) {
        String deviceToken = userFindService.getDeviceToken(sendUserId);
        return NotificationForOneDto.builder()
                .title(FOLLOW_ACCEPT_TITLE)
                .message(FOLLOW_ACCEPT_MESSAGE)
                .deviceToken(deviceToken)
                .build();
    }
}
