package com.yapp.ios1.service;

import com.yapp.ios1.dto.notification.NotificationForOneDto;
import com.yapp.ios1.dto.user.result.FriendDto;
import com.yapp.ios1.mapper.AlarmMapper;
import com.yapp.ios1.mapper.FollowMapper;
import com.yapp.ios1.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.yapp.ios1.utils.follow.FollowNotification.*;
import static com.yapp.ios1.utils.follow.FriendStatus.FRIEND;
import static com.yapp.ios1.utils.follow.FriendStatus.REQUEST;

/**
 * created by jg 2021/05/21
 */
@RequiredArgsConstructor
@Service
public class FollowService {

    private final NotificationService notificationService;
    private final FollowMapper followMapper;
    private final AlarmMapper alarmMapper;
    private final UserMapper userMapper;

    @Transactional
    public void followRequest(Long myUserId, Long friendId) {
        NotificationForOneDto notificationForOne = makeSendAlarmMessage(friendId, FOLLOW_REQUEST_TITLE.getMessage(), FOLLOW_REQUEST_MESSAGE.getMessage());
        alarmMapper.insertFollowAlarmLog(notificationForOne, LocalDateTime.now(), friendId);
        followMapper.followRequest(myUserId, friendId, REQUEST.getFriendStatus(), notificationForOne.getAlarmId());
        sendFollowAlarmRequest(notificationForOne);  // 알람 보내기
    }

    private NotificationForOneDto makeSendAlarmMessage(Long friendId, String title, String message) {
        String deviceToken = userMapper.findDeviceTokenByUserId(friendId);
        return NotificationForOneDto.builder()
                .title(title)
                .message(message)
                .deviceToken(deviceToken)
                .build();

    }

    private void sendFollowAlarmRequest(NotificationForOneDto notificationForOne) {
        notificationService.sendByToken(notificationForOne);
    }

    // 친구 리스트
    public List<FriendDto> getFriendList(Long userId) {
        return followMapper.getFollowListByUserId(userId);
    }

    @Transactional
    public void checkFollowStatus(boolean isAccept, Long myUserId, Long friendId, Long alarmId) {
        if (isAccept) {
            followAccept(myUserId, friendId);
            return;
        }
        followNotAccept(myUserId, alarmId);
    }

    private void followAccept(Long myUserId, Long friendId) {
        NotificationForOneDto notificationForOne = makeSendAlarmMessage(friendId, FOLLOW_ACCEPT_TITLE.getMessage(), FOLLOW_ACCEPT_MESSAGE.getMessage());
        // 친구 요청 수락
        alarmMapper.insertFollowAlarmLog(notificationForOne, LocalDateTime.now(), friendId);
        followMapper.followAccept(myUserId, friendId, FRIEND.getFriendStatus());
        sendFollowAlarmRequest(notificationForOne);  // 요청 보낸 사람에게 알람 보내기
    }

    private void followNotAccept(Long myUserId, Long alarmId) {
        alarmMapper.deleteAlarmLog(myUserId, alarmId);
    }
}
