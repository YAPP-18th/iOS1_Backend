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
import com.yapp.ios1.model.notification.Notification;
import com.yapp.ios1.mapper.AlarmMapper;
import com.yapp.ios1.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

import static com.yapp.ios1.common.AlarmMessage.WHOLE_ALARM_MESSAGE;
import static com.yapp.ios1.common.AlarmMessage.WHOLE_ALARM_TITLE;

/**
 * created by jg 2021/05/02
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class NotificationService {

    @Value("${fcm.account.path}")
    private String accountPath;

    private final UserMapper userMapper;
    private final AlarmMapper alarmMapper;

    @PostConstruct
    public void init() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.
                            fromStream(new ClassPathResource(accountPath).getInputStream())).build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("Firebase Cloud Messaging 서비스를 성공적으로 초기화하였습니다.");
            }
        } catch (IOException e) {
            log.error("cannot initial firebase " + e.getMessage());
        }
    }

    @Async("asyncTask")
    public void sendPushNotification() {
        List<String> deviceTokens = findDeviceTokens();
        NotificationDto pushNotificationRequest = makeNotification();

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

    @Async("asyncTask")
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
            // FireBaseMessage Throw Refactor
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

    // 초, 분, 시간, 일, 월, 요일 (매월, 1일, 20시 53분 30초에 알림을 보내도록 임시로 설정)
    @Scheduled(cron = "10 12 14 * * ?", zone = "Asia/Seoul")
    @Transactional
    public void notificationSchedule() {
        alarmMapper.insertWholeAlarmLog(makeNotification());
        sendPushNotification();
    }

    private NotificationDto makeNotification() {
        return new NotificationDto(WHOLE_ALARM_TITLE, WHOLE_ALARM_MESSAGE, LocalDateTime.now());
    }

    private List<String> findDeviceTokens() {
        return userMapper.findAllUserDeviceToken();
    }

    public void deleteAlarm(Long userId, Long alarmId) {
        alarmMapper.deleteAlarmLog(userId, alarmId);
    }
}

