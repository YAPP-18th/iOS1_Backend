package com.yapp.ios1.controller;

import com.yapp.ios1.dto.notification.NotificationDto;
import com.yapp.ios1.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by jg 2021/05/17
 */
@RequiredArgsConstructor
@RestController
public class AlarmController {

    private final NotificationService notificationService;

    // 삭제할 예정
    @GetMapping("/alarm")
    public String testAlarm() {
        NotificationDto notificationDto = NotificationDto.builder()
                .title("제목")
                .message("아령하세요")
                .build();
        notificationService.sendPushNotification(notificationDto);
        return "Alarm";
    }
}
