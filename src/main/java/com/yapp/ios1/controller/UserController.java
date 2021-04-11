package com.yapp.ios1.controller;

import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.dto.user.UserDto;
import com.yapp.ios1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by jg 2021/03/28
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
public class UserController {

    private final UserService userService;

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
}
