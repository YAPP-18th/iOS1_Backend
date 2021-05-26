package com.yapp.ios1.controller;

import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.service.NotificationService;
import com.yapp.ios1.utils.auth.Auth;
import com.yapp.ios1.utils.auth.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by jg 2021/05/24
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
public class AlarmController {

    private final NotificationService notificationService;

    // 개발 용 API
    @GetMapping("/alarm")
    public String alarmTest() {
        notificationService.notificationSchedule();
        return "test";
    }

    @Auth
    @GetMapping("/alarm-log")
    public ResponseEntity<ResponseDto> alarmLog() {
        Long userId = UserContext.getCurrentUserId();
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.OK, "테스트", notificationService.getAlarmLog(userId)));
    }
}
