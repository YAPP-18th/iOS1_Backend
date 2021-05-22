package com.yapp.ios1.mapper;

import com.yapp.ios1.dto.notification.NotificationForOneDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * created by jg 2021/05/21
 */
@Mapper
public interface AlarmMapper {

    void insertAlarmLog(@Param("notification") NotificationForOneDto notification,
                        @Param("friendId") Long friendId);
}
