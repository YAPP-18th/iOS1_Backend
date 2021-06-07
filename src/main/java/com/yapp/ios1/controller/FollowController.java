package com.yapp.ios1.controller;

import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.service.FollowService;
import com.yapp.ios1.utils.auth.Auth;
import com.yapp.ios1.utils.auth.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.yapp.ios1.common.ResponseMessage.FRIEND_MESSAGE;
import static com.yapp.ios1.common.ResponseMessage.FRIEND_REQUEST;

/**
 * created by jg 2021/05/21
 */
@Api(tags = "Follow")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/follow")
public class FollowController {

    private final FollowService followService;

    @ApiOperation(value = "친구 요청")
    @Auth
    @PostMapping("/request/{friendId}")
    public ResponseEntity<ResponseDto> followRequest(@PathVariable Long friendId) {
        Long myUserId = UserContext.getCurrentUserId();
        followService.followRequest(myUserId, friendId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.CREATED, FRIEND_REQUEST));
    }

    @ApiOperation(value = "친구 요청 수락, 거절")
    @Auth
    @PostMapping("/{friendId}/{alarmId}")
    public ResponseEntity<ResponseDto> followAccept(@PathVariable Long friendId,
                                                    @PathVariable Long alarmId,
                                                    @RequestParam("accept") boolean isAccept) {
        Long myUserId = UserContext.getCurrentUserId();
        followService.checkFollowStatus(isAccept, myUserId, friendId, alarmId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.CREATED, FRIEND_MESSAGE, isAccept));
    }
}
