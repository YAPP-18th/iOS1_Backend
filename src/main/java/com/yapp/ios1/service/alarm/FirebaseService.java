package com.yapp.ios1.service.alarm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.*;
import com.yapp.ios1.dto.notification.FcmMessage;
import com.yapp.ios1.dto.notification.NotificationDto;
import com.yapp.ios1.dto.notification.NotificationForOneDto;
import com.yapp.ios1.model.user.User;
import com.yapp.ios1.properties.FirebaseProperties;
import com.yapp.ios1.service.user.UserFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.yapp.ios1.message.AlarmMessage.WHOLE_ALARM_MESSAGE;
import static com.yapp.ios1.message.AlarmMessage.WHOLE_ALARM_TITLE;

/**
 * created by jg 2021/05/02
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class FirebaseService {

    private final UserFindService userFindService;
    private final FirebaseProperties firebaseProperties;
    private final RestTemplate restTemplate;
    private String fcmAccessToken;

    @PostConstruct
    public void init() {
        try {
            GoogleCredentials googleCredentials =
                    GoogleCredentials.fromStream(new ClassPathResource(firebaseProperties.getPath()).getInputStream())
                            .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
            googleCredentials.refreshIfExpired();
            fcmAccessToken = googleCredentials.getAccessToken().getTokenValue();
        } catch (IOException e) {
            log.error("cannot initial firebase " + e.getMessage());
        }
    }

    public void sendByTokenForMulti() {
        List<String> deviceTokens = userFindService.getAllDeviceToken();
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

    public void sendByTokenForOne(NotificationForOneDto messageInfo, Long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(fcmAccessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        FcmMessage message = makeMessage(messageInfo, userId);

        HttpEntity<Object> request = new HttpEntity<>(message, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "https://fcm.googleapis.com/v1/projects/buok-ios/messages:send",
                    HttpMethod.POST,
                    request,
                    String.class
            );
            log.info("send message: " + response);
        } catch (Exception e) {
            log.error("cannot send message by token. error info : {}", e.getMessage());
        }
    }

    private FcmMessage makeMessage(NotificationForOneDto messageInfo, Long userId) {
        User user = userFindService.getUser(userId);
        return FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(messageInfo.getDeviceToken())
                        .notification(FcmMessage.Notification.builder()
                                .title(messageInfo.getTitle())
                                .body(user.getNickname() + messageInfo.getMessage())
                                .build())
                        .build()
                )
                .build();
    }

    public NotificationDto getWholeAlarmMessage() {
        return new NotificationDto(WHOLE_ALARM_TITLE, WHOLE_ALARM_MESSAGE, LocalDateTime.now());
    }
}

