package com.yapp.ios1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yapp.ios1.dto.JwtPayload;
import com.yapp.ios1.dto.user.NicknameCheckDto;
import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.dto.user.*;
import com.yapp.ios1.exception.user.UserDuplicatedException;
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

import java.sql.SQLException;
import java.util.Optional;

/**
 * created by jg 2021/03/28
 */
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v2/user")
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
    @PostMapping("/check-email")
    public ResponseEntity<ResponseDto> emailCheck(@RequestBody EmailCheckDto emailDto) throws SQLException {
        Optional<UserDto> user = userService.emailCheck(emailDto.getEmail());
        ResponseDto response;
        if (user.isEmpty()) {
            response = ResponseDto.of(HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다.");
        } else {
            response = ResponseDto.of(HttpStatus.OK, "회원이 존재합니다.", user.get().getNickname());
        }
        return ResponseEntity.ok().body(response);
    }

    /**
     * 닉네임 확인
     *
     * @param nicknameDto 닉네임
     */
    @ApiOperation(
            value = "닉네임 존재 여부"
    )
    @PostMapping("/check-nickname")
    public ResponseEntity<ResponseDto> nicknameCheck(@RequestBody NicknameCheckDto nicknameDto) throws SQLException {
        Optional<UserDto> user = userService.nicknameCheck(nicknameDto.getNickname());
        ResponseDto response;
        if (user.isEmpty()) {
            response = ResponseDto.of(HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다.");
        } else {
            response = ResponseDto.of(HttpStatus.OK, "회원이 존재합니다.");
        }
        return ResponseEntity.ok().body(response);
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
            throw new UserDuplicatedException("이미 존재하는 계정입니다.");
        }

        userService.signUp(signUpDto);
        ResponseDto response = ResponseDto.of(HttpStatus.CREATED, "회원가입이 완료되었습니다.");
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
    public ResponseEntity<ResponseDto> signIn(@RequestBody SignInDto signInDto) throws JsonProcessingException, SQLException {
        UserDto userDto = userService.getMember(signInDto);
        String token = jwtService.createToken(new JwtPayload(userDto.getId()));
        ResponseDto response = ResponseDto.of(HttpStatus.OK, "로그인이 완료되었습니다.", new TokenDto(token));
        return ResponseEntity.ok().body(response);
    }

    // 테스트 입니다 ~
    @ApiOperation(
            value = "인증 테스트"
    )
    @Auth
    @GetMapping("/test")
    public ResponseEntity<ResponseDto> tokenTest() throws JsonProcessingException {
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.OK, "테스트", new TokenDto(jwtService.createToken(new JwtPayload(1L)))));
    }
}
