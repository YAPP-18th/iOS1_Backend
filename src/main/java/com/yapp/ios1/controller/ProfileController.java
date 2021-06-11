package com.yapp.ios1.controller;

import com.yapp.ios1.annotation.Auth;
import com.yapp.ios1.aop.UserContext;
import com.yapp.ios1.controller.dto.user.ProfileUpdateDto;
import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.service.ProfileService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.yapp.ios1.message.ResponseMessage.GET_PROFILE_SUCCESS;
import static com.yapp.ios1.message.ResponseMessage.UPDATE_PROFILE_SUCCESS;

/**
 * created by jg 2021/06/11
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/profile")  // TODO 뭐가 좋을지 생각
public class ProfileController {

    private final ProfileService profileService;

    @ApiOperation(value = "프로필 가져오기")
    @Auth
    @GetMapping("")
    public ResponseEntity<ResponseDto> getProfile() {
        Long userId = UserContext.getCurrentUserId();
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, GET_PROFILE_SUCCESS, profileService.getProfile(userId)));
    }

    @ApiOperation(value = "프로필 수정")
    @Auth
    @PutMapping("")
    public ResponseEntity<ResponseDto> updateProfile(@RequestBody ProfileUpdateDto profile) {
        Long userId = UserContext.getCurrentUserId();
        profileService.updateProfile(profile, userId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, UPDATE_PROFILE_SUCCESS));
    }
}
