package com.yapp.ios1.service;

import com.yapp.ios1.dto.notification.NotificationForOneDto;
import com.yapp.ios1.model.user.User;
import com.yapp.ios1.model.user.Friend;
import com.yapp.ios1.mapper.AlarmMapper;
import com.yapp.ios1.mapper.FollowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.yapp.ios1.common.AlarmMessage.*;
import static com.yapp.ios1.common.AlarmStatus.FOLLOW_ALARM;
import static com.yapp.ios1.common.FriendStatus.FRIEND;
import static com.yapp.ios1.common.FriendStatus.REQUEST;

/**
 * created by jg 2021/05/21
 */
@RequiredArgsConstructor
@Service
public class FollowService {

    private final NotificationService notificationService;
    private final FollowMapper followMapper;
    private final AlarmMapper alarmMapper;
    private final UserService userService;

    @Transactional
    public void followRequest(Long myUserId, Long friendId) {
        User user = userService.findByUserId(friendId);
        NotificationForOneDto notificationForOne = makeSendAlarmMessage(friendId, FOLLOW_REQUEST_TITLE, user.getNickname() + FOLLOW_REQUEST_MESSAGE);
        alarmMapper.insertFollowAlarmLog(notificationForOne, FOLLOW_ALARM.getAlarmStatus(), LocalDateTime.now(), friendId);   // alarm_status = 2 (친구 알람)
        followMapper.followRequest(myUserId, friendId, REQUEST.getFriendStatus(), notificationForOne.getAlarmId());
        sendFollowAlarmRequest(notificationForOne);
    }

    private NotificationForOneDto makeSendAlarmMessage(Long friendId, String title, String message) {
        String deviceToken = userService.findDeviceTokenByUserId(friendId);
        return NotificationForOneDto.builder()
                .title(title)
                .message(message)
                .deviceToken(deviceToken)
                .build();
    }

    private void sendFollowAlarmRequest(NotificationForOneDto notificationForOne) {
        notificationService.sendByToken(notificationForOne);
    }

    public List<Friend> getFriendList(Long userId) {
        return followMapper.getFollowListByUserId(userId);
    }

    // friendStatus = 1(친구), 2(요청 중)
    @Transactional
    public void checkFollowStatus(boolean isAccept, Long myUserId, Long friendId, Long alarmId) {
        if (isAccept) {
            followAccept(myUserId, friendId);
            return;
        }
        followNotAccept(myUserId, alarmId);
    }

    private void followAccept(Long myUserId, Long friendId) {
        NotificationForOneDto notificationForOne = makeSendAlarmMessage(friendId, FOLLOW_ACCEPT_TITLE, FOLLOW_ACCEPT_MESSAGE);
        alarmMapper.insertFollowAlarmLog(notificationForOne, FOLLOW_ALARM.getAlarmStatus(), LocalDateTime.now(), friendId);
        followMapper.followAccept(myUserId, friendId, FRIEND.getFriendStatus());
        sendFollowAlarmRequest(notificationForOne);
    }

    private void followNotAccept(Long myUserId, Long alarmId) {
        alarmMapper.deleteFollowAlarmLog(myUserId, alarmId);
    }
}
