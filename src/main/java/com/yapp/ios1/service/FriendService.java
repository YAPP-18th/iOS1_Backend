package com.yapp.ios1.service;

import com.yapp.ios1.dto.notification.NotificationForOneDto;
import com.yapp.ios1.model.user.User;
import com.yapp.ios1.model.user.Friend;
import com.yapp.ios1.mapper.AlarmMapper;
import com.yapp.ios1.mapper.FriendMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.yapp.ios1.message.AlarmMessage.*;
import static com.yapp.ios1.enums.AlarmStatus.FOLLOW_ALARM;
import static com.yapp.ios1.enums.FriendStatus.FRIEND;
import static com.yapp.ios1.enums.FriendStatus.REQUEST;

/**
 * created by jg 2021/05/21
 */
@RequiredArgsConstructor
@Service
public class FriendService {

    private final FirebaseService firebaseService;
    private final FriendMapper followMapper;
    private final AlarmMapper alarmMapper;
    private final UserService userService;

    @Transactional
    public void requestFollow(Long myUserId, Long friendId) {
        User user = userService.getUser(friendId);
        NotificationForOneDto notificationForOne = firebaseService.makeSendAlarmMessage(friendId, FOLLOW_REQUEST_TITLE, user.getNickname() + FOLLOW_REQUEST_MESSAGE);
        alarmMapper.insertFollowAlarmLog(notificationForOne, FOLLOW_ALARM.get(), LocalDateTime.now(), friendId);   // alarm_status = 2 (친구 알람)
        followMapper.requestFollow(myUserId, friendId, REQUEST.get(), notificationForOne.getAlarmId());
        sendFollowAlarmRequest(notificationForOne);
    }

    private void sendFollowAlarmRequest(NotificationForOneDto notificationForOne) {
        firebaseService.sendByToken(notificationForOne);
    }

    public List<Friend> getFriendList(Long userId) {
        return followMapper.getFollowListByUserId(userId);
    }

    // friendStatus = 1(친구), 2(요청 중)
    @Transactional
    public void checkFollowStatus(boolean isAccept, Long myUserId, Long friendId, Long alarmId) {
        if (isAccept) {
            acceptFollow(myUserId, friendId);
            return;
        }
        followNotAccept(myUserId, alarmId);
    }

    private void acceptFollow(Long myUserId, Long friendId) {
        NotificationForOneDto notificationForOne = firebaseService.makeSendAlarmMessage(friendId, FOLLOW_ACCEPT_TITLE, FOLLOW_ACCEPT_MESSAGE);
        alarmMapper.insertFollowAlarmLog(notificationForOne, FOLLOW_ALARM.get(), LocalDateTime.now(), friendId);
        followMapper.acceptFollow(myUserId, friendId, FRIEND.get());
        sendFollowAlarmRequest(notificationForOne);
    }

    private void followNotAccept(Long myUserId, Long alarmId) {
        alarmMapper.deleteFollowAlarmLog(myUserId, alarmId);
    }
}
