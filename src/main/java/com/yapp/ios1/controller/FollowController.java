package com.yapp.ios1.controller;

import com.yapp.ios1.common.ResponseMessage;
import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.dto.user.result.FriendDto;
import com.yapp.ios1.service.FollowService;
import com.yapp.ios1.utils.auth.Auth;
import com.yapp.ios1.utils.auth.UserContext;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yapp.ios1.common.ResponseMessage.GET_FRIEND_LIST;
import static com.yapp.ios1.common.ResponseMessage.NO_FRIEND_LIST;

/**
 * created by jg 2021/05/21
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("follow")
public class FollowController {

    private final FollowService followService;

    @ApiOperation(value = "친구 요청")
    @Auth
    @PostMapping("/request/{friendId}")
    public ResponseEntity<ResponseDto> followRequest(@PathVariable Long friendId) {
        Long myUserId = UserContext.getCurrentUserId();

        followService.followRequest(myUserId, friendId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.CREATED, ResponseMessage.FRIEND_REQUEST));
    }

    @ApiOperation(value = "친구 요청 수락, 거절")
    @Auth
    @PostMapping("/{friendId}/{alarmId}")
    public ResponseEntity<ResponseDto> followAccept(@PathVariable Long friendId,
                                                    @PathVariable Long alarmId,
                                                    @RequestParam("accept") boolean isAccept) {
        Long myUserId = UserContext.getCurrentUserId();
        followService.checkFollowStatus(isAccept, myUserId, friendId, alarmId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.CREATED, ResponseMessage.FRIEND_MESSAGE, isAccept));
    }

    @ApiOperation(value = "친구 리스트")
    @ApiResponses({
            @ApiResponse(code = 200, message = "친구 목록 존재하는 경우")
    })
    @GetMapping("/users/{userId}/friends")
    public ResponseEntity<ResponseDto> getFriendList(@PathVariable Long userId) {
        List<FriendDto> friendList = followService.getFriendList(userId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, GET_FRIEND_LIST, friendList));
    }
}
