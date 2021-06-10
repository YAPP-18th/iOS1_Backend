package com.yapp.ios1.service;

import com.yapp.ios1.mapper.AlarmMapper;
import com.yapp.ios1.model.notification.Notification;
import com.yapp.ios1.validaor.AlarmValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.yapp.ios1.enums.AlarmStatus.WHOLE_ALARM;

/**
 * created by jg 2021/06/10
 */
@RequiredArgsConstructor
@Service
public class AlarmService {

    private final AlarmMapper alarmMapper;
    private final AlarmValidator alarmValidator;

    public List<Notification> getAlarmLog(Long userId) {
        List<Notification> followAlarmLog = alarmMapper.getFollowAlarmLog(userId);
        List<Notification> commonAlarmLog = alarmMapper.getCommonAlarmLog(userId);

        return Stream.concat(followAlarmLog.stream(), commonAlarmLog.stream())
                .sorted(Comparator.comparing(Notification::getCreatedAt))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteAlarm(Long alarmId, Long userId, int alarmStatus) {
        if (alarmStatus == WHOLE_ALARM.get()) {
            alarmValidator.checkValidWholeAlarm(alarmId);
            alarmMapper.deleteWholeAlarmLog(alarmId, userId);
            return;
        }
        // 친구 알람 삭제
        alarmValidator.checkValidFollowAlarm(alarmId);
        alarmMapper.deleteFollowAlarmLog(alarmId, userId);
    }
}
