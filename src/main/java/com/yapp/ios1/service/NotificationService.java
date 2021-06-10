package com.yapp.ios1.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.yapp.ios1.dto.notification.NotificationDto;
import com.yapp.ios1.dto.notification.NotificationForOneDto;
import com.yapp.ios1.error.exception.alarm.AlarmNotFoundException;
import com.yapp.ios1.model.notification.Notification;
import com.yapp.ios1.mapper.AlarmMapper;
import com.yapp.ios1.mapper.UserMapper;
import com.yapp.ios1.properties.FirebaseProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.yapp.ios1.message.AlarmMessage.WHOLE_ALARM_MESSAGE;
import static com.yapp.ios1.message.AlarmMessage.WHOLE_ALARM_TITLE;
import static com.yapp.ios1.enums.AlarmStatus.WHOLE_ALARM;

/**
 * created by jg 2021/05/02
 */
@RequiredArgsConstructor
@Slf4j
@Service
// TODO FireBaseService(알람을 보내는, 초기화 하는 코드만 존재), AlarmService(친구 요청 수락, 거절 등등) 분리해보기
public class NotificationService {

    private final UserService userService;
    private final AlarmMapper alarmMapper;
    private final FirebaseProperties firebaseProperties;

    @PostConstruct
    public void init() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.
                            fromStream(new ClassPathResource(firebaseProperties.getPath()).getInputStream())).build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("Firebase Cloud Messaging 서비스를 성공적으로 초기화하였습니다.");
            }
        } catch (IOException e) {
            log.error("cannot initial firebase " + e.getMessage());
        }
    }

    public void sendPushNotification() {
        List<String> deviceTokens = userService.getAllDeviceToken();
        NotificationDto pushNotificationRequest = getWholeAlarmMessage();

        List<Message> messages = deviceTokens.stream().map(token -> Message.builder()
                .putData("title", pushNotificationRequest.getTitle())
                .putData("message", pushNotificationRequest.getMessage())
                .setToken(token)
                .build()).collect(Collectors.toList());

        BatchResponse response;
        try {
            response = FirebaseMessaging.getInstance().sendAll(messages);
            log.info("send message: " + response);
        } catch (FirebaseMessagingException e) {
            log.error("cannot send to member push message. error info : {}", e.getMessage());
        }
    }

    public void sendByToken(NotificationForOneDto messageInfo) {
        Message message = Message.builder()
                .setToken(messageInfo.getDeviceToken())
                .putData("title", messageInfo.getTitle())
                .putData("message", messageInfo.getMessage())
                .build();

        String response;
        try {
            response = FirebaseMessaging.getInstance().send(message);
            log.info("send message: " + response);
        } catch (FirebaseMessagingException e) {
            log.error("cannot send message by token. error info : {}", e.getMessage());
        }
    }

    public List<Notification> getAlarmLog(Long userId) {
        List<Notification> followAlarmLog = alarmMapper.getFollowAlarmLog(userId);
        List<Notification> commonAlarmLog = alarmMapper.getCommonAlarmLog(userId);

        return Stream.concat(followAlarmLog.stream(), commonAlarmLog.stream())
                .sorted(Comparator.comparing(Notification::getCreatedAt))
                .collect(Collectors.toList());
    }

    private NotificationDto getWholeAlarmMessage() {
        return new NotificationDto(WHOLE_ALARM_TITLE, WHOLE_ALARM_MESSAGE, LocalDateTime.now());
    }

    // 초, 분, 시간, 일, 월, 요일 (매월, 1일, 20시 53분 30초에 알림을 보내도록 임시로 설정)
    @Scheduled(cron = "10 12 14 * * ?", zone = "Asia/Seoul")
    @Transactional
    public void notificationSchedule() {
        alarmMapper.insertWholeAlarmLog(getWholeAlarmMessage(), WHOLE_ALARM.get());  // alarm_status = 1 (전체 알람)
        sendPushNotification();
    }

    @Transactional
    public void deleteAlarm(Long alarmId, Long userId, int alarmStatus) {
        if (alarmStatus == WHOLE_ALARM.get()) {
            checkValidWholeAlarm(alarmId);
            alarmMapper.deleteWholeAlarmLog(alarmId, userId);
            return;
        }
        // 친구 알람 삭제
        checkValidFollowAlarm(alarmId);
        alarmMapper.deleteFollowAlarmLog(alarmId, userId);
    }

    private void checkValidWholeAlarm(Long alarmId) {
        alarmMapper.findWholeAlarmByAlarmId(alarmId)
                .orElseThrow(AlarmNotFoundException::new);
    }

    private void checkValidFollowAlarm(Long alarmId) {
        alarmMapper.findFollowAlarmByAlarmId(alarmId)
                .orElseThrow(AlarmNotFoundException::new);
    }
}

