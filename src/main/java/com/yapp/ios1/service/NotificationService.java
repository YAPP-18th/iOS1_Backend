package com.yapp.ios1.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import com.yapp.ios1.common.ResponseMessage;
import com.yapp.ios1.dto.notification.NotificationDto;
import com.yapp.ios1.dto.notification.NotificationForOneDto;
import com.yapp.ios1.exception.notification.FirebaseNotInitException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * created by jg 2021/05/02
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    @Value("${fcm.account.path}")
    private String accountPath;

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
    public void sendPushNotification(NotificationDto pushNotificationRequest, String deviceToken) {

        List<String> tokens = Arrays.asList(
                //"cYm9R_j7ReuSFz6Z2xZT6r:APA91bFgFquTCqTFXFYDK69kNrS_dRTCxdIPw7frEyG8IfcQ9AyovzS8sz-dhjCJoQTwXKI0G_IvcMy4Ae80Woou5SyeMyJ8faJd2ifPR-JsuSJofMIduyfoEHUcOsLarOTOnR162PFI"
                deviceToken
        );

        List<Message> messages = tokens.stream().map(token -> Message.builder()
                .putData("title", pushNotificationRequest.getTitle())
                .putData("message", pushNotificationRequest.getMessage())
                .setToken(token)
                .build()).collect(Collectors.toList());

        // 여러명 알람 한테 보내기
        BatchResponse response;
        try {
            response = FirebaseMessaging.getInstance().sendAll(messages);
            log.info("Sent message: " + response);
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
            log.info("Sent message: " + response);
        } catch (FirebaseMessagingException e) {
            log.error("cannot send message by token. error info : {}", e.getMessage());
        }
    }
}

