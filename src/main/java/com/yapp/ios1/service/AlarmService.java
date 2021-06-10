package com.yapp.ios1.service;

import com.yapp.ios1.mapper.AlarmMapper;
import com.yapp.ios1.model.notification.Notification;
import com.yapp.ios1.validaor.AlarmValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
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
    private final FirebaseService firebaseService;
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

    // 초, 분, 시간, 일, 월, 요일 (매월, 1일, 20시 53분 30초에 알림을 보내도록 임시로 설정)
    @Scheduled(cron = "10 12 14 * * ?", zone = "Asia/Seoul")
    @Transactional
    public void notificationSchedule() {
        alarmMapper.insertWholeAlarmLog(firebaseService.getWholeAlarmMessage(), WHOLE_ALARM.get());  // alarm_status = 1 (전체 알람)
        firebaseService.sendPushNotification();
    }
}
