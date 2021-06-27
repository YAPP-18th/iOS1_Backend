package com.yapp.ios1.controller;

import com.yapp.ios1.annotation.Auth;
import com.yapp.ios1.aop.UserContext;
import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.model.user.Friend;
import com.yapp.ios1.service.FriendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

import static com.yapp.ios1.message.ResponseMessage.*;

/**
 * created by jg 2021/05/21
 */
@Api(tags = "Friend")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
public class FriendController {

    private final FriendService friendService;

    @ApiOperation("친구 요청")
    @Auth
    @PostMapping("/friend/{friendId}/request")
    public ResponseEntity<ResponseDto> followRequest(@PathVariable Long friendId) {
        Long myUserId = UserContext.getCurrentUserId();
        friendService.requestFollow(myUserId, friendId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.CREATED, FRIEND_REQUEST));
    }

    @ApiOperation("친구 요청 수락, 거절")
    @Auth
    @PostMapping("/friend/{friendId}/{alarmId}")
    public ResponseEntity<ResponseDto> followAccept(@PathVariable Long friendId,
                                                    @PathVariable Long alarmId,
                                                    @RequestParam("accept") boolean isAccept) {
        Long myUserId = UserContext.getCurrentUserId();
        friendService.checkFollowAccept(isAccept, myUserId, friendId, alarmId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.CREATED, FRIEND_MESSAGE, isAccept));
    }

    @ApiOperation("친구 리스트")
    @GetMapping("/users/{userId}/friends")
    public ResponseEntity<ResponseDto> getFriendList(@PathVariable Long userId) {
        List<Friend> friendList = friendService.getFriendList(userId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, GET_FRIEND_LIST, friendList));
    }

    @Auth
    @ApiOperation("친구 끊기")
    @DeleteMapping("/friend/{friendId}")
    public ResponseEntity<ResponseDto> deleteFriend(@PathVariable Long friendId) {
        Long myUserId = UserContext.getCurrentUserId();
        friendService.deleteFriend(myUserId, friendId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, FRIEND_DELETE));
    }
}
