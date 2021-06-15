package com.yapp.ios1.utils;

import com.yapp.ios1.dto.notification.NotificationForOneDto;
import com.yapp.ios1.model.user.User;
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

    public NotificationForOneDto getFollowAlarmMessage(Long getUserId, Long sendUserId) {
        User user = userFindService.getUser(getUserId);
        return createFollowAlarmMessage(sendUserId, FOLLOW_ACCEPT_TITLE, user.getNickname() + FOLLOW_ACCEPT_MESSAGE);
    }

    private NotificationForOneDto createFollowAlarmMessage(Long sendUserId, String title, String message) {
        String deviceToken = userFindService.getDeviceToken(sendUserId);
        return NotificationForOneDto.builder()
                .title(title)
                .message(message)
                .deviceToken(deviceToken)
                .build();
    }
}
