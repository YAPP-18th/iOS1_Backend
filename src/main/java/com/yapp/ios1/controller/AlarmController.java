package com.yapp.ios1.controller;

import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.dto.notification.NotificationForOneDto;
import com.yapp.ios1.dto.notification.NotificationTestDto;
import com.yapp.ios1.service.alarm.AlarmService;
import com.yapp.ios1.annotation.Auth;
import com.yapp.ios1.aop.UserContext;
import com.yapp.ios1.service.alarm.FirebaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.yapp.ios1.message.ResponseMessage.DELETE_ALARM_LOG;
import static com.yapp.ios1.message.ResponseMessage.GET_ALARM_LOG;

/**
 * created by jg 2021/05/24
 */
@Api(tags = "Alarm")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/alarm")
public class AlarmController {

    private final AlarmService alarmService;
    private final FirebaseService firebaseService;

    @ApiOperation("알람 로그 조회")
    @Auth
    @GetMapping("")
    public ResponseEntity<ResponseDto> alarmLog() {
        Long userId = UserContext.getCurrentUserId();
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, GET_ALARM_LOG, alarmService.getAlarmLog(userId)));
    }

    @ApiOperation("알람 로그 삭제 (alarmStatus: 1(전체 알람), 2(친구 알람))")
    @Auth
    @DeleteMapping("/{alarmId}")
    public ResponseEntity<ResponseDto> deleteAlarm(@PathVariable Long alarmId, @RequestParam("alarm_status") int alarmStatus) {
        Long userId = UserContext.getCurrentUserId();
        alarmService.deleteAlarm(alarmId, userId, alarmStatus);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, DELETE_ALARM_LOG));
    }

//    @ApiOperation("푸시 알림 테스트")
//    @PostMapping("/test")
//    public ResponseEntity<ResponseDto> alarmTest(@RequestBody NotificationTestDto requestDto) {
//        NotificationForOneDto notificationForOne = NotificationForOneDto.builder()
//                .title(requestDto.getTitle())
//                .message(requestDto.getMessage())
//                .deviceToken(requestDto.getDeviceToken())
//                .build();
//        firebaseService.sendByTokenForOne(notificationForOne);
//        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, "푸시 알림 보냄"));
//    }
}
