package com.yapp.ios1.utils;

import com.yapp.ios1.dto.notification.NotificationForOneDto;
import com.yapp.ios1.model.user.User;
import com.yapp.ios1.service.UserService;
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

    private final UserService userService;

    public NotificationForOneDto getAlarmMessage(Long getUserId, Long sendUserId) {
        User user = userService.getUser(getUserId);
        return createAlarmMessage(sendUserId, FOLLOW_ACCEPT_TITLE, user.getNickname() + FOLLOW_ACCEPT_MESSAGE);
    }

    private NotificationForOneDto createAlarmMessage(Long sendUserId, String title, String message) {
        String deviceToken = userService.getDeviceToken(sendUserId);
        return NotificationForOneDto.builder()
                .title(title)
                .message(message)
                .deviceToken(deviceToken)
                .build();
    }
}
