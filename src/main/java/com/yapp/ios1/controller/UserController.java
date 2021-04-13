package com.yapp.ios1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yapp.ios1.dto.JwtPayload;
import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.dto.user.TokenDto;
import com.yapp.ios1.dto.user.UserDto;
import com.yapp.ios1.service.JwtService;
import com.yapp.ios1.service.UserService;
import com.yapp.ios1.utils.auth.Auth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * MyBatis 세팅을 위한 임시 코드 (삭제 예정)
     * + ResponseDto 테스트를 위한 코드 추가 (by ayoung 2021/04/10)
     */
    @GetMapping("/")
    public ResponseEntity<ResponseDto> test() {
        UserDto userDto = new UserDto("wjdrbs966@gmail.com", "KAKAO");
        userService.test(userDto);
        ResponseDto response = ResponseDto.of(HttpStatus.OK, "응답 테스트 메세지", userDto);
        return ResponseEntity.ok().body(response);
    }

    /**
     * 이메일 확인
     *
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

    // 테스트 입니다 ~
    @Auth
    @GetMapping("/test")
    public ResponseEntity<ResponseDto> tokenTest(Long id) throws JsonProcessingException {
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.OK, "테스트", new TokenDto(jwtService.createToken(new JwtPayload(1L)))));
    }
}
