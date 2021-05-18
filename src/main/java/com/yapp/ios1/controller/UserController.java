package com.yapp.ios1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yapp.ios1.dto.JwtPayload;
import com.yapp.ios1.dto.user.check.EmailCheckDto;
import com.yapp.ios1.dto.user.check.NicknameCheckDto;
import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.dto.user.*;
import com.yapp.ios1.dto.user.login.SignInDto;
import com.yapp.ios1.dto.user.login.SignUpDto;
import com.yapp.ios1.dto.user.login.TokenDto;
import com.yapp.ios1.dto.user.result.FriendDto;
import com.yapp.ios1.exception.user.UserDuplicatedException;
import com.yapp.ios1.service.JwtService;
import com.yapp.ios1.service.UserService;
import com.yapp.ios1.utils.auth.Auth;
import com.yapp.ios1.utils.auth.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.yapp.ios1.common.ResponseMessage.*;

/**
 * created by jg 2021/03/28
 */
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v2/users")
@Api(tags = "User")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    /**
     * 이메일 확인
     *
     * @param emailDto 이메일
     */
    @ApiOperation(
            value = "이메일 존재 여부"
    )
    @PostMapping("/email-check")
    public ResponseEntity<ResponseDto> emailCheck(@RequestBody EmailCheckDto emailDto) {
        Optional<UserDto> user = userService.emailCheck(emailDto.getEmail());
        if (user.isEmpty()) {
            return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.NOT_FOUND, NOT_EXIST_USER));
        }
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, EXIST_USER, user.get().getNickname()));
    }

    /**
     * 닉네임 확인
     *
     * @param nicknameDto 닉네임
     */
    @ApiOperation(
            value = "닉네임 존재 여부"
    )
    @PostMapping("/nickname-check")
    public ResponseEntity<ResponseDto> nicknameCheck(@RequestBody NicknameCheckDto nicknameDto) {
        Optional<UserDto> user = userService.nicknameCheck(nicknameDto.getNickname());
        if (user.isEmpty()) {
            return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.NOT_FOUND, NOT_EXIST_USER));
        }
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, EXIST_USER));
    }

    /**
     * 회원가입
     *
     * @param signUpDto 회원가입 정보
     */
    @ApiOperation(
            value = "회원가입"
    )
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signUp(@RequestBody SignUpDto signUpDto) throws SQLException {
        Optional<UserDto> user = userService.signUpCheck(signUpDto.getEmail(), signUpDto.getNickname());
        if (user.isPresent()) {
            throw new UserDuplicatedException(EXIST_USER);
        }

        userService.signUp(UserDto.of(signUpDto));
        ResponseDto response = ResponseDto.of(HttpStatus.CREATED, SIGN_UP_SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    /**
     * 로그인
     *
     * @param signInDto 로그인 정보
     */
    @ApiOperation(
            value = "로그인"
    )
    @PostMapping("/signin")
    public ResponseEntity<ResponseDto> signIn(@RequestBody SignInDto signInDto) throws JsonProcessingException {
        UserDto userDto = userService.getUser(signInDto);
        String token = jwtService.createToken(new JwtPayload(userDto.getId()));
        ResponseDto response = ResponseDto.of(HttpStatus.OK, LOGIN_SUCCESS, new TokenDto(token));
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(
            value = "프로필 가져오기"
    )
    @Auth
    @GetMapping("")
    public ResponseEntity<ResponseDto> getProfile() {
        Long userId = UserContext.getCurrentUserId();
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK,GET_PROFILE_SUCCESS, userService.getProfile(userId)));
    }

    @ApiOperation(
            value = "프로필 수정",
            notes = "포스트맨에서 테스트 가능"
    )
    @Auth
    @PutMapping("")
    public ResponseEntity<ResponseDto> updateProfile(@RequestPart(value = "profileImage", required = false) MultipartFile profileImage,
                                                     @RequestPart ProfileDto profile) throws IOException {
        Long userId = UserContext.getCurrentUserId();
        userService.updateProfile(profile, profileImage, userId);
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, UPDATE_PROFILE_SUCCESS));
    }

    @ApiOperation(
            value = "마이 페이지"
    )
    @Auth
    @GetMapping("/me")
    public ResponseEntity<ResponseDto> getMyInfo() {
        Long userId = UserContext.getCurrentUserId();

        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.OK, GET_MY_INFO, userService.getUserInfo(userId)));
    }

    @ApiOperation(
            value = "사용자 페이지"
    )
    @Auth
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDto> getUserInfo(@PathVariable Long userId) {
        Long currentUserId = UserContext.getCurrentUserId();
        if (currentUserId.equals(userId)) {
            return ResponseEntity.ok()
                    .body(ResponseDto.of(HttpStatus.OK, GET_MY_INFO, userService.getUserInfo(userId)));

        }
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.OK, GET_USER_INFO, userService.getOtherUserInfo(currentUserId, userId)));
    }

    @ApiOperation(
            value = "친구 리스트"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "친구 목록 존재하는 경우"),
            @ApiResponse(code = 404, message = "친구 목록 존재하지 않는 경우"),
    })
    @GetMapping("/{userId}/friends")
    public ResponseEntity<ResponseDto> getFriendList(@PathVariable Long userId) {
        List<FriendDto> friendList = userService.getFriendList(userId);
        if (friendList.size() == 0) {
            return ResponseEntity.ok()
                    .body(ResponseDto.of(HttpStatus.NOT_FOUND, NO_FRIEND_LIST));
        }
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.OK, GET_FRIEND_LIST, friendList));
    }

}
