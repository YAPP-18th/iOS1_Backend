package com.yapp.ios1.mapper;

import com.yapp.ios1.dto.notification.NotificationDto;
import com.yapp.ios1.dto.notification.NotificationForOneDto;
import com.yapp.ios1.model.notification.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * created by jg 2021/05/21
 */
@Mapper
public interface AlarmMapper {

    void insertFollowAlarmLog(@Param("notification") NotificationForOneDto notification,
                              @Param("nickName") String nickName,
                              @Param("alarmStatus") int alarmStatus,
                              @Param("time") LocalDateTime localDateTime,
                              @Param("friendId") Long friendId);

    void updateFollowAlarmLog(@Param("notification") NotificationForOneDto notification,
                              @Param("alarmId") Long alarmId);

    void insertWholeAlarmLog(@Param("alarm") NotificationDto notificationDto,
                             @Param("alarmStatus") int alarmStatus);

    List<Notification> getCommonAlarmLog(Long userId);
    List<Notification> getFollowAlarmLog(Long userId);

    void deleteFollowAlarmLog(Long alarmId, Long userId);
    void deleteWholeAlarmLog(Long alarmId, Long userId);

    Optional<Notification> findWholeAlarmByAlarmId(Long alarmId);
    Optional<Notification> findFollowAlarmByAlarmId(Long alarmId);
}
