package com.yapp.ios1.mapper;

import com.yapp.ios1.dto.notification.NotificationDto;
import com.yapp.ios1.dto.notification.NotificationForOneDto;
import com.yapp.ios1.dto.notification.response.NotificationFollowLogDto;
import com.yapp.ios1.dto.notification.response.NotificationWholeLogDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * created by jg 2021/05/21
 */
@Mapper
public interface AlarmMapper {

    void insertAlarmLog(@Param("notification") NotificationForOneDto notification,
                        @Param("friendId") Long friendId);

    void insertWholeAlarmLog(@Param("alarm") NotificationDto notificationDto);

    List<NotificationWholeLogDto> getCommonAlarmLog(Long userId);

    List<NotificationFollowLogDto> getFollowAlarmLog(Long userId);

    void insertAlarmLogBatch(List<NotificationDto> list);

    void insertAlarm(NotificationDto notificationDto);
}
