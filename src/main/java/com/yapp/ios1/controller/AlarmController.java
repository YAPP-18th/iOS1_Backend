package com.yapp.ios1.controller;

import com.yapp.ios1.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by jg 2021/05/24
 */
@RequiredArgsConstructor
@RestController
public class AlarmController {

    private final NotificationService notificationService;

    @GetMapping("/alarm")
    public String alarmTest() {
        return "test";
    }
}
