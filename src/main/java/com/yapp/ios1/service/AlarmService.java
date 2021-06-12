package com.yapp.ios1.service;

import com.yapp.ios1.mapper.AlarmMapper;
import com.yapp.ios1.mapper.UserMapper;
import com.yapp.ios1.model.notification.Notification;
import com.yapp.ios1.validator.AlarmValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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
    private final UserMapper userMapper;
    private final FirebaseService firebaseService;
    private final AlarmValidator alarmValidator;

    public List<Notification> getAlarmLog(Long userId) {
        List<Notification> followAlarmLog = alarmMapper.getFollowAlarmLog(userId);
        List<Notification> commonAlarmLog = alarmMapper.getCommonAlarmLog(userId);
        // 알림 로그 읽었다는 뜻
        userMapper.updateAlarmStatus(userId, true);

        return Stream.concat(followAlarmLog.stream(), commonAlarmLog.stream())
                .sorted(Comparator.comparing(Notification::getCreatedAt))
                .collect(Collectors.toList());
    }

    public void deleteAlarm(Long alarmId, Long userId, int alarmStatus) {
        if (alarmStatus == WHOLE_ALARM.get()) {
            alarmValidator.checkValidWholeAlarm(alarmId);
            alarmMapper.deleteWholeAlarmLog(alarmId, userId);
            return;
        }

        alarmValidator.checkValidFollowAlarm(alarmId);
        alarmMapper.deleteFollowAlarmLog(alarmId, userId);
    }

    // 초, 분, 시간, 일, 월, 요일 (매월, 1일, 20시 53분 30초에 알림을 보내도록 임시로 설정)
    @Scheduled(cron = "10 12 14 * * ?", zone = "Asia/Seoul")
    public void notificationSchedule() {
        alarmMapper.insertWholeAlarmLog(firebaseService.getWholeAlarmMessage(), WHOLE_ALARM.get());  // alarm_status = 1 (전체 알람)
        firebaseService.sendByTokenForMulti();
    }
}
