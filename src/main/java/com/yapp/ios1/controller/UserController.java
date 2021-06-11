package com.yapp.ios1.controller;

import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.controller.dto.user.ProfileUpdateDto;
import com.yapp.ios1.model.user.User;
import com.yapp.ios1.controller.dto.user.login.PasswordDto;
import com.yapp.ios1.controller.dto.user.login.SignInDto;
import com.yapp.ios1.controller.dto.user.login.SignUpDto;
import com.yapp.ios1.model.user.Friend;
import com.yapp.ios1.service.FriendService;
import com.yapp.ios1.service.JwtService;
import com.yapp.ios1.service.UserService;
import com.yapp.ios1.aop.Auth;
import com.yapp.ios1.aop.UserContext;
import com.yapp.ios1.validator.UserValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.yapp.ios1.message.ResponseMessage.*;

/**
 * created by jg 2021/03/28
 */
@Api(tags = "User")
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v2/users")
public class UserController {

    private final UserService userService;
    private final UserValidator userValidator;
    private final JwtService jwtService;
    private final FriendService followService;

    @ApiOperation(value = "이메일 존재 여부")
    @GetMapping("/email-check")
    public ResponseEntity<ResponseDto> emailCheck(@RequestParam String email) {
        userValidator.checkEmailDuplicate(email);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, POSSIBLE_EMAIL));
    }

    @ApiOperation(value = "닉네임 존재 여부")
    @GetMapping("/nickname-check")
    public ResponseEntity<ResponseDto> nicknameCheck(@RequestParam String nickname) {
        userValidator.checkNickName(nickname);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, POSSIBLE_NICKNAME));
    }

    @ApiOperation(value = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signUp(@RequestBody @Valid SignUpDto signUpDto) {
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.CREATED, SIGN_UP_SUCCESS, userService.signUp(User.of(signUpDto))));
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/signin")
    public ResponseEntity<ResponseDto> signIn(@RequestBody @Valid SignInDto signInDto) {
        User user = userValidator.checkPassword(signInDto);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, LOGIN_SUCCESS, jwtService.createTokenResponse(user.getId())));
    }

    @ApiOperation(value = "비밀번호 재설정")
    @Auth
    @PutMapping("/password")
    public ResponseEntity<ResponseDto> changePassword(@RequestBody PasswordDto passwordDto) {
        Long userId = UserContext.getCurrentUserId();
        userService.changePassword(userId, passwordDto.getPassword());
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, CHANGE_PASSWORD_SUCCESS));
    }

    @ApiOperation(value = "마이 페이지")
    @Auth
    @GetMapping("/me")
    public ResponseEntity<ResponseDto> getMyInfo() {
        Long userId = UserContext.getCurrentUserId();
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, GET_MY_INFO, userService.getUserInfo(userId)));
    }

    @ApiOperation(value = "사용자 페이지")
    @Auth
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDto> getUserInfo(@PathVariable Long userId) {
        Long currentUserId = UserContext.getCurrentUserId();
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, GET_USER_INFO, userService.getOtherUserInfo(currentUserId, userId)));
    }

    // TODO User 가 지금 너무 거대해지고 Friend 모델을 사용하고 있기 때문에 FriendController로 이동하는 게 좋을 거 같음
    @ApiOperation(value = "친구 리스트")
    @GetMapping("/{userId}/friends")
    public ResponseEntity<ResponseDto> getFriendList(@PathVariable Long userId) {
        List<Friend> friendList = followService.getFriendList(userId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, GET_FRIEND_LIST, friendList));
    }

    @ApiOperation(value = "회원 탈퇴")
    @Auth
    @DeleteMapping("")
    public ResponseEntity<ResponseDto> deleteUser() {
        userService.deleteUser(UserContext.getCurrentUserId());
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, DELETE_USER_SUCCESS));
    }
}
