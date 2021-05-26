package com.yapp.ios1.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.yapp.ios1.common.ResponseMessage;
import com.yapp.ios1.dto.notification.NotificationDto;
import com.yapp.ios1.dto.notification.NotificationForOneDto;
import com.yapp.ios1.exception.notification.FirebaseNotInitException;
import com.yapp.ios1.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            throw new FirebaseNotInitException(ResponseMessage.FIREBASE_INIT_ERROR);
        }
    }

    @Async("asyncTask")
    public void sendPushNotification(NotificationDto pushNotificationRequest) {
        List<String> deviceTokens = findDeviceTokens();

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
            log.error("cannot send message by token. error info : {}", e.getMessage());
        }
    }

    // 초, 분, 시간, 일, 월, 요일
    // 매월, 1일, 20시 53분 30초에 알림을 보내도록 임시로 설정
    //@Scheduled(cron = "10 12 14 * * ?", zone = "Asia/Seoul")
    @Async("asyncTask")
    public void notificationSchedule() {
        NotificationDto notificationDto = new NotificationDto("제목", "메세지");
        alarmLogBatchInsert(notificationDto);
        sendPushNotification(notificationDto);
    }

    private void alarmLogBatchInsert(NotificationDto notificationDto) {
        int userNumber = findDeviceTokens().size();
        List<NotificationDto> alarmBatch = new ArrayList<>();

        for (int i = 0; i < userNumber; ++i) {
            alarmBatch.add(notificationDto);
        }

        userMapper.insertFullAlarmLog(alarmBatch);
    }

    private List<String> findDeviceTokens() {
        // TODO 레디스에 DeviceToken 전부 넣어서 가져오도록 고도화 시키기
        return userMapper.findAllUserDeviceToken();
    }
}

