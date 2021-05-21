package com.yapp.ios1.controller;

import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.service.FollowService;
import com.yapp.ios1.utils.auth.Auth;
import com.yapp.ios1.utils.auth.UserContext;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by jg 2021/05/21
 */
@RequiredArgsConstructor
@RestController
public class FollowController {

    private final FollowService followService;

    @ApiOperation(
            value = "팔로우 요청"
    )
    @Auth
    @PostMapping("/follow/{friendId}")
    public ResponseEntity<ResponseDto> followRequest(@PathVariable Long friendId) {
        Long myUserId = UserContext.getCurrentUserId();

        followService.followRequest(myUserId, friendId);
        return null;
    }
}
