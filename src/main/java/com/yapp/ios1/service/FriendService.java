package com.yapp.ios1.service;

import com.yapp.ios1.dto.notification.NotificationForOneDto;
import com.yapp.ios1.model.user.Friend;
import com.yapp.ios1.mapper.AlarmMapper;
import com.yapp.ios1.mapper.FriendMapper;
import com.yapp.ios1.model.user.User;
import com.yapp.ios1.service.alarm.FirebaseService;
import com.yapp.ios1.service.user.UserFindService;
import com.yapp.ios1.service.user.UserService;
import com.yapp.ios1.utils.AlarmMessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
    private final UserFindService userFindService;
    private final AlarmMessageUtil alarmMessage;

    // TODO 리팩터링
    @Transactional
    public void requestFollow(Long myUserId, Long friendId) {
        NotificationForOneDto notificationForOne = alarmMessage.createFollowAlarmMessage(friendId);
        // alarm_status = 1(전체알람), 2 (친구 알람)
        User user = userFindService.getUser(myUserId);
        alarmMapper.insertFollowAlarmLog(notificationForOne, user.getNickname(), FOLLOW_ALARM.get(), LocalDateTime.now(), friendId);
        followMapper.requestFollow(myUserId, friendId, REQUEST.get(), notificationForOne.getAlarmId());
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
        noAcceptFollow(myUserId, alarmId);
    }

    // TODO 리팩터링
    private void acceptFollow(Long myUserId, Long friendId, Long alarmId) {
        NotificationForOneDto notificationForOne = alarmMessage.createFollowAlarmMessage(friendId);
        alarmMapper.updateFollowAlarmLog(notificationForOne, alarmId);
        followMapper.acceptFollow(myUserId, friendId, FRIEND.get());
        userService.updateUserAlarmReadStatus(friendId, false);
        firebaseService.sendByTokenForOne(notificationForOne);
    }

    private void noAcceptFollow(Long myUserId, Long alarmId) {
        alarmMapper.deleteFollowAlarmLog(myUserId, alarmId);
    }
}
