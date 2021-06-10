package com.yapp.ios1.controller;

import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.service.FriendService;
import com.yapp.ios1.aop.Auth;
import com.yapp.ios1.aop.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.yapp.ios1.message.ResponseMessage.FRIEND_MESSAGE;
import static com.yapp.ios1.message.ResponseMessage.FRIEND_REQUEST;

/**
 * created by jg 2021/05/21
 */
@Api(tags = "Follow")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/friend")
public class FriendController {

    private final FriendService followService;

    @ApiOperation(value = "친구 요청")
    @Auth
    @PostMapping("/{friendId}/request")
    public ResponseEntity<ResponseDto> followRequest(@PathVariable Long friendId) {
        Long myUserId = UserContext.getCurrentUserId();
        followService.requestFollow(myUserId, friendId);
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
