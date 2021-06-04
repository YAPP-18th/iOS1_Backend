package com.yapp.ios1.controller;

import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.dto.user.ProfileDto;
import com.yapp.ios1.dto.user.UserDto;
import com.yapp.ios1.dto.user.check.EmailDto;
import com.yapp.ios1.dto.user.check.NicknameCheckDto;
import com.yapp.ios1.dto.user.login.PasswordDto;
import com.yapp.ios1.dto.user.login.SignInDto;
import com.yapp.ios1.dto.user.login.SignUpDto;
import com.yapp.ios1.error.exception.user.EmailDuplicatedException;
import com.yapp.ios1.service.JwtService;
import com.yapp.ios1.service.UserService;
import com.yapp.ios1.utils.auth.Auth;
import com.yapp.ios1.utils.auth.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static com.yapp.ios1.common.ResponseMessage.*;

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
    private final JwtService jwtService;

    /**
     * 이메일 확인
     *
     * @param emailDto 이메일
     */
    @ApiOperation(value = "이메일 존재 여부")
    @PostMapping("/email-check")
    public ResponseEntity<ResponseDto> emailCheck(@RequestBody @Valid EmailDto emailDto) {
        userService.emailCheck(emailDto.getEmail());
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, EXIST_USER));
    }

    /**
     * 닉네임 확인
     *
     * @param nicknameDto 닉네임
     */
    @ApiOperation(value = "닉네임 존재 여부")
    @PostMapping("/nickname-check")
    public ResponseEntity<ResponseDto> nicknameCheck(@RequestBody NicknameCheckDto nicknameDto) {
        Optional<UserDto> user = userService.nicknameCheck(nicknameDto.getNickname());
        if (user.isEmpty()) {
            return ResponseEntity.ok(ResponseDto.of(HttpStatus.NOT_FOUND, NOT_EXIST_USER));
        }
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, EXIST_USER));
    }

    /**
     * 회원가입
     *
     * @param signUpDto 회원가입 정보
     */
    @ApiOperation(value = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signUp(@RequestBody @Valid SignUpDto signUpDto) {
        Optional<UserDto> user = userService.signUpCheck(signUpDto.getEmail(), signUpDto.getNickname());
        if (user.isPresent()) {
            throw new EmailDuplicatedException();
        }

        Long userId = userService.signUp(UserDto.of(signUpDto));
        ResponseDto response = ResponseDto.of(HttpStatus.CREATED, SIGN_UP_SUCCESS, jwtService.createTokenResponse(userId));
        return ResponseEntity.ok(response);
    }

    /**
     * 로그인
     *
     * @param signInDto 로그인 정보
     */
    @ApiOperation(
            value = "로그인",
            notes = "로그인 성공 시, accessToken과 refreshToken을 발급합니다."
    )
    @PostMapping("/signin")
    public ResponseEntity<ResponseDto> signIn(@RequestBody @Valid SignInDto signInDto) {
        UserDto userDto = userService.getUser(signInDto);
        ResponseDto response = ResponseDto.of(HttpStatus.OK, LOGIN_SUCCESS, jwtService.createTokenResponse(userDto.getId()));
        return ResponseEntity.ok(response);
    }

    @ApiOperation(
            value = "비밀번호 재설정",
            notes = "요청 헤더에 토큰, 요청 바디에 비밀번호 전달"
    )
    @Auth
    @PutMapping("/password")
    public ResponseEntity<ResponseDto> changePassword(@RequestBody PasswordDto passwordDto) {
        Long userId = UserContext.getCurrentUserId();
        userService.changePassword(userId, passwordDto.getPassword());
        ResponseDto response = ResponseDto.of(HttpStatus.OK, CHANGE_PASSWORD_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "프로필 가져오기")
    @Auth
    @GetMapping("")
    public ResponseEntity<ResponseDto> getProfile() {
        Long userId = UserContext.getCurrentUserId();
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, GET_PROFILE_SUCCESS, userService.getProfile(userId)));
    }

    @ApiOperation(value = "프로필 수정")
    @Auth
    @PutMapping("")
    public ResponseEntity<ResponseDto> updateProfile(@RequestBody ProfileDto profile) {
        Long userId = UserContext.getCurrentUserId();
        userService.updateProfile(profile, userId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, UPDATE_PROFILE_SUCCESS));
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
        if (currentUserId.equals(userId)) {
            return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, GET_MY_INFO, userService.getUserInfo(userId)));

        }
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, GET_USER_INFO, userService.getOtherUserInfo(currentUserId, userId)));
    }
}
