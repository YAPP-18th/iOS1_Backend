package com.yapp.ios1.mapper;

import com.yapp.ios1.dto.notification.NotificationDto;
import com.yapp.ios1.dto.notification.NotificationForOneDto;
import com.yapp.ios1.dto.notification.response.NotificationLogResultDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * created by jg 2021/05/21
 */
@Mapper
public interface AlarmMapper {

    void insertFollowAlarmLog(@Param("notification") NotificationForOneDto notification,
                              @Param("time") LocalDateTime localDateTime,
                              @Param("friendId") Long friendId);

    void insertWholeAlarmLog(@Param("alarm") NotificationDto notificationDto);

    List<NotificationLogResultDto> getCommonAlarmLog(Long userId);

    List<NotificationLogResultDto> getFollowAlarmLog(Long userId);

    void deleteAlarmLog(Long userId, Long alarmId);
}
