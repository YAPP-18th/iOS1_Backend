package com.yapp.ios1.controller;

import com.yapp.ios1.common.ResponseMessage;
import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.service.NotificationService;
import com.yapp.ios1.utils.auth.Auth;
import com.yapp.ios1.utils.auth.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * created by jg 2021/05/24
 */
@Api(tags = "Alarm")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
public class AlarmController {

    private final NotificationService notificationService;

    @ApiOperation(
            value = "알람 로그 조회"
    )
    @Auth
    @GetMapping("/alarm")
    public ResponseEntity<ResponseDto> alarmLog() {
        Long userId = UserContext.getCurrentUserId();
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.GET_ALARM_LOG, notificationService.getAlarmLog(userId)));
    }

    @ApiOperation(
            value = "알람 로그 삭제"
    )
    @Auth
    @DeleteMapping("/alarm/{alarmId}")
    public ResponseEntity<ResponseDto> deleteAlarm(@PathVariable Long alarmId) {
        Long userId = UserContext.getCurrentUserId();
        notificationService.deleteAlarm(userId, alarmId);
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.DELETE_ALARM_LOG));
    }

}
