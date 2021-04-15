package com.yapp.ios1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yapp.ios1.dto.JwtPayload;
import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.dto.user.SignUpDto;
import com.yapp.ios1.dto.user.TokenDto;
import com.yapp.ios1.dto.user.UserDto;
import com.yapp.ios1.exception.UserDuplicatedException;
import com.yapp.ios1.service.JwtService;
import com.yapp.ios1.service.UserService;
import com.yapp.ios1.utils.auth.Auth;
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
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    /**
     * 이메일 확인
     * @param email 이메일
     */
    @PostMapping("/check")
    public ResponseEntity<ResponseDto> emailCheck(@RequestBody String email) {
        Optional<UserDto> user = userService.emailCheck(email);
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
     * @param signUpDto 회원가입 정보
     */
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signUp(@RequestBody SignUpDto signUpDto) throws SQLException {
        Optional<UserDto> user = userService.emailCheck(signUpDto.getEmail());
        ResponseDto response;
        if (user.isPresent()) {
            throw new UserDuplicatedException(signUpDto.getEmail());
        } else {
            userService.signUp(signUpDto);
            response = ResponseDto.of(HttpStatus.CREATED, "회원가입이 완료되었습니다.");
        }
        return ResponseEntity.ok().body(response);
    }

    // 테스트 입니다 ~
    @Auth
    @GetMapping("/test")
    public ResponseEntity<ResponseDto> tokenTest(Long id) throws JsonProcessingException {
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.OK, "테스트", new TokenDto(jwtService.createToken(new JwtPayload(1L)))));
    }
}
