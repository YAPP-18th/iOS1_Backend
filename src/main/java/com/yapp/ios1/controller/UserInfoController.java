package com.yapp.ios1.controller;

import com.yapp.ios1.annotation.Auth;
import com.yapp.ios1.aop.UserContext;
import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.service.user.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.yapp.ios1.message.ResponseMessage.GET_MY_INFO;
import static com.yapp.ios1.message.ResponseMessage.GET_USER_INFO;

/**
 * created by ayoung 2021/06/12
 */
@Api(tags = "User Info")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/users")
public class UserInfoController {

    private final UserInfoService userInfoService;

    @ApiOperation(value = "마이 페이지")
    @Auth
    @GetMapping("/me")
    public ResponseEntity<ResponseDto> getMyInfo() {
        Long userId = UserContext.getCurrentUserId();
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, GET_MY_INFO, userInfoService.getUserInfo(userId)));
    }

    @ApiOperation(value = "사용자 페이지")
    @Auth
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDto> getUserInfo(@PathVariable Long userId) {
        Long currentUserId = UserContext.getCurrentUserId();
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, GET_USER_INFO, userInfoService.getOtherUserInfo(currentUserId, userId)));
    }
}
