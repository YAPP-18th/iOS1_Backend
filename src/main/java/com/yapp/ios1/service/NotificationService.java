package com.yapp.ios1.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import com.yapp.ios1.dto.notification.NotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        try (InputStream serviceAccount = Files.newInputStream(Paths.get(System.getProperty("user.dir") + accountPath))) {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("Firebase Cloud Messaging 서비스를 성공적으로 초기화하였습니다.");
            }


        } catch (IOException e) {
            log.error("cannot initial firebase " + e.getMessage());
        }
    }


    public void sendPushNotification(NotificationDto pushNotificationRequest) {

        // 임시 테스트 (저의 DeviceToken)
        List<String> tokens = Arrays.asList(
                "cYm9R_j7ReuSFz6Z2xZT6r:APA91bFgFquTCqTFXFYDK69kNrS_dRTCxdIPw7frEyG8IfcQ9AyovzS8sz-dhjCJoQTwXKI0G_IvcMy4Ae80Woou5SyeMyJ8faJd2ifPR-JsuSJofMIduyfoEHUcOsLarOTOnR162PFI"
        );

        List<Message> messages = tokens.stream().map(token -> Message.builder()
                .putData("title", pushNotificationRequest.getTitle())
                .putData("message", pushNotificationRequest.getMessage())
                .setToken(token)
                .build()).collect(Collectors.toList());

        // 여러명 한테 보내기
        BatchResponse response;
        try {
            response = FirebaseMessaging.getInstance().sendAll(messages);
            log.info("Sent message: " + response);
        } catch (FirebaseMessagingException e) {
            log.error("cannot send to member push message. error info : {}", e.getMessage());
        }
    }

    // 한명한테 알람 보내기
    private Message writePushMessage(NotificationDto pushNotificationRequest, String token) {
        return Message.builder()
                .setWebpushConfig(WebpushConfig.builder()
                        .setNotification(WebpushNotification.builder()
                                .setTitle(pushNotificationRequest.getTitle())
                                .setBody(pushNotificationRequest.getMessage())
                                .build())
                        .build())
                .setToken(token)
                .build();
    }
}

