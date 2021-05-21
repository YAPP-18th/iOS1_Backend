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

import static com.yapp.ios1.dto.follow.FriendStatus.REQUEST;
import static com.yapp.ios1.dto.notification.AlarmLogStatus.ACTIVITY;

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
        followMapper.followRequest(myUserId, friendId, REQUEST.getFriendStatus());
        NotificationForOneDto notification = sendFollowAlarmRequest(friendId);
        alarmMapper.insertAlarmLog(notification, friendId, ACTIVITY.getAlarmStatus());
    }

    private NotificationForOneDto sendFollowAlarmRequest(Long friendId) {
        String deviceToken = userMapper.findDeviceTokenByUserId(1L);   // Redis 에서 꺼내오는 걸로 고도화 예정 (1L 는 임시)
        NotificationForOneDto notificationDto = NotificationForOneDto.builder()
                .title("팔로우 제목")
                .message("팔로우 신청 메세지")
                .deviceToken(deviceToken)
                .build();
        notificationService.sendByToken(notificationDto);
        return notificationDto;
    }


    // 친구 리스트
    public List<FriendDto> getFriendList(Long userId) {
        return followMapper.getFollowListByUserId(userId);
    }
}
