package com.yapp.ios1.service;

import com.yapp.ios1.dto.notification.NotificationForOneDto;
import com.yapp.ios1.dto.user.UserDto;
import com.yapp.ios1.dto.user.result.FriendDto;
import com.yapp.ios1.mapper.AlarmMapper;
import com.yapp.ios1.mapper.FollowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.yapp.ios1.common.FriendMessage.*;
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
        UserDto user = userService.findByUserId(friendId);
        System.out.println(user.getNickname());
        NotificationForOneDto notificationForOne =
                makeSendAlarmMessage(friendId, FOLLOW_REQUEST_TITLE, user.getNickname() + FOLLOW_REQUEST_MESSAGE);
        alarmMapper.insertFollowAlarmLog(notificationForOne, LocalDateTime.now(), friendId);
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

    public List<FriendDto> getFriendList(Long userId) {
        return followMapper.getFollowListByUserId(userId);
    }

    // friendStatus = 1(친구 아님), 2(친구)
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
        // 친구 요청 수락
        alarmMapper.insertFollowAlarmLog(notificationForOne, LocalDateTime.now(), friendId);
        followMapper.followAccept(myUserId, friendId, FRIEND.getFriendStatus());
        sendFollowAlarmRequest(notificationForOne);  // 요청 보낸 사람에게 알람 보내기
    }

    private void followNotAccept(Long myUserId, Long alarmId) {
        alarmMapper.deleteAlarmLog(myUserId, alarmId);
    }
}
