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
public class FriendService {  // TODO 전체적인 리팩터링

    private final FirebaseService firebaseService;
    private final FriendMapper followMapper;
    private final AlarmMapper alarmMapper;
    private final UserService userService;

    // TODO 리팩터링
    @Transactional
    public void requestFollow(Long myUserId, Long friendId) {
        // TODO 35, 36은 외부에서 주입 받는게 나은것인가..
        User user = userService.getUser(friendId);
        NotificationForOneDto notificationForOne = firebaseService.makeSendAlarmMessage(friendId, FOLLOW_REQUEST_TITLE, user.getNickname() + FOLLOW_REQUEST_MESSAGE);
        alarmMapper.insertFollowAlarmLog(notificationForOne, FOLLOW_ALARM.get(), LocalDateTime.now(), friendId);   // alarm_status = 2 (친구 알람)
        followMapper.requestFollow(myUserId, friendId, REQUEST.get(), notificationForOne.getAlarmId());
        userService.updateUserAlarmReadStatus(friendId, false);
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
    public void checkFollowAccept(boolean isAccept, Long myUserId, Long friendId, Long alarmId) {
        if (isAccept) {
            acceptFollow(myUserId, friendId);
            return;
        }
        noAcceptFollow(myUserId, alarmId);
    }

    private void acceptFollow(Long myUserId, Long friendId) {
        NotificationForOneDto notificationForOne = firebaseService.makeSendAlarmMessage(friendId, FOLLOW_ACCEPT_TITLE, FOLLOW_ACCEPT_MESSAGE);
        alarmMapper.insertFollowAlarmLog(notificationForOne, FOLLOW_ALARM.get(), LocalDateTime.now(), friendId);
        followMapper.acceptFollow(myUserId, friendId, FRIEND.get());
        userService.updateUserAlarmReadStatus(friendId, false);
        sendFollowAlarmRequest(notificationForOne);
    }

    private void noAcceptFollow(Long myUserId, Long alarmId) {
        alarmMapper.deleteFollowAlarmLog(myUserId, alarmId);
    }
}
