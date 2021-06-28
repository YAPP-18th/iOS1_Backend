package com.yapp.ios1.service;

import com.yapp.ios1.dto.notification.NotificationForOneDto;
import com.yapp.ios1.mapper.AlarmMapper;
import com.yapp.ios1.mapper.FriendMapper;
import com.yapp.ios1.model.user.Friend;
import com.yapp.ios1.service.alarm.FirebaseService;
import com.yapp.ios1.service.user.UserService;
import com.yapp.ios1.utils.AlarmMessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.yapp.ios1.enums.AlarmStatus.FOLLOW_ALARM;
import static com.yapp.ios1.enums.FriendStatus.FRIEND;
import static com.yapp.ios1.enums.FriendStatus.REQUEST;
import static com.yapp.ios1.message.AlarmMessage.*;

/**
 * created by jg 2021/05/21
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FriendService {

    private final FirebaseService firebaseService;
    private final FriendMapper followMapper;
    private final AlarmMapper alarmMapper;
    private final UserService userService;
    private final AlarmMessageUtil alarmMessage;

    // TODO 어떻게 리팩터링 할까
    @Transactional
    public void requestFollow(Long myUserId, Long friendId) {
        NotificationForOneDto notificationForOne = alarmMessage.createFollowAlarmMessage(FOLLOW_REQUEST_TITLE, FOLLOW_REQUEST_MESSAGE, friendId);
        // alarm_status = 1(전체알람), 2 (친구 알람)
        alarmMapper.insertFollowAlarmLog(notificationForOne, myUserId, FOLLOW_ALARM.get(), LocalDateTime.now(), friendId);
        followMapper.insertFollow(myUserId, friendId, REQUEST.get(), notificationForOne.getAlarmId());
        userService.updateUserAlarmReadStatus(friendId, false);
        firebaseService.sendByTokenForOne(notificationForOne);
    }

    public List<Friend> getFriendList(Long userId) {
        return followMapper.getFollowListByUserId(userId);
    }

    // friendStatus = 1(친구), 2(요청 중)
    @Transactional
    public void checkFollowAccept(boolean isAccept, Long myUserId, Long friendId, Long alarmId) {
        if (isAccept) {
            acceptFollow(myUserId, friendId, alarmId);
            return;
        }
        noAcceptFollow(alarmId);
    }

    // TODO 어떻게 리팩터링 할까
    private void acceptFollow(Long myUserId, Long friendId, Long alarmId) {
        NotificationForOneDto notificationForOne = alarmMessage.createFollowAlarmMessage(FOLLOW_ACCEPT_TITLE, FOLLOW_ACCEPT_MESSAGE, friendId);
        alarmMapper.updateFollowAlarmLog(notificationForOne, alarmId);
        alarmMapper.insertFollowAlarmLog(notificationForOne, myUserId, FOLLOW_ALARM.get(), LocalDateTime.now(), friendId);
        followMapper.insertFollow(myUserId, friendId, FRIEND.get(), notificationForOne.getAlarmId());
        followMapper.updateFriendStatus(myUserId, friendId, FRIEND.get());
        userService.updateUserAlarmReadStatus(friendId, false);
        firebaseService.sendByTokenForOne(notificationForOne);
    }

    private void noAcceptFollow(Long alarmId) {
        alarmMapper.deleteFollowAlarmLog(alarmId);
    }

    // TODO : 좋지 않은 API (어떻게 리팩터링 할까
    @Transactional
    public void deleteFriend(Long myUserId, Long friendId) {
        Long followRequestAlarmId = getFollowAlarmId(myUserId, friendId);
        Long followAcceptAlarmId = getFollowAlarmId(friendId, myUserId);
        alarmMapper.deleteFollowAlarmLog(followRequestAlarmId);
        alarmMapper.deleteRequestAndAcceptFollowAlarmLog(followRequestAlarmId, followAcceptAlarmId);
        followMapper.deleteFriend(myUserId, friendId);
    }

    private Long getFollowAlarmId(Long myUserId, Long friendId) {
        return followMapper.findByFollowAlarmId(myUserId, friendId);
    }
}
