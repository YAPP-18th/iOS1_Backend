package com.yapp.ios1.validaor;

import com.yapp.ios1.error.exception.alarm.AlarmNotFoundException;
import com.yapp.ios1.mapper.AlarmMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * created by jg 2021/06/10
 */
@RequiredArgsConstructor
@Component
public class AlarmValidator {

    private final AlarmMapper alarmMapper;

    public void checkValidWholeAlarm(Long alarmId) {
        alarmMapper.findWholeAlarmByAlarmId(alarmId)
                .orElseThrow(AlarmNotFoundException::new);
    }

    public void checkValidFollowAlarm(Long alarmId) {
        alarmMapper.findFollowAlarmByAlarmId(alarmId)
                .orElseThrow(AlarmNotFoundException::new);
    }
}

