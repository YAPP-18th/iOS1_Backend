package com.yapp.ios1.service;

import com.yapp.ios1.dto.notification.NotificationForOneDto;
import com.yapp.ios1.dto.user.result.FriendDto;
import com.yapp.ios1.mapper.AlarmMapper;
import com.yapp.ios1.mapper.FollowMapper;
import com.yapp.ios1.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 팔로우 신청, 알람 로그 저장, 알람 보내기
     * @param myUserId
     * @param friendId
     */
    @Transactional
    public void followRequest(Long myUserId, Long friendId) {
        NotificationForOneDto notificationForOne = makeSendAlarmMessage(friendId, FOLLOW_REQUEST_TITLE.getMessage(), FOLLOW_REQUEST_MESSAGE.getMessage());
        alarmMapper.insertFollowAlarmLog(notificationForOne, friendId);
        followMapper.followRequest(myUserId, friendId, REQUEST.getFriendStatus());
        sendFollowAlarmRequest(notificationForOne);  // 알람 보내기
    }

    private NotificationForOneDto makeSendAlarmMessage(Long friendId, String title, String message) {
        // TODO Redis 에서 꺼내오는 걸로 고도화 예정 (1L 는 임시)
        String deviceToken = userMapper.findDeviceTokenByUserId(1L);
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

    /**
     * 친구 요청 승낙
     * @param myUserId
     * @param friendId
     */
    @Transactional
    public void followAccept(Long myUserId, Long friendId) {
        NotificationForOneDto notificationForOne = makeSendAlarmMessage(friendId, FOLLOW_ACCEPT_TITLE.getMessage(), FOLLOW_ACCEPT_MESSAGE.getMessage());
        followMapper.followAccept(myUserId, friendId, FRIEND.getFriendStatus());
        alarmMapper.insertFollowAlarmLog(notificationForOne, friendId);
        sendFollowAlarmRequest(notificationForOne);
    }
}
