package com.yapp.ios1.controller;

import com.yapp.ios1.dto.user.UserDto;
import com.yapp.ios1.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
     */
    @GetMapping("/")
    public String test() {
        UserDto userDto = new UserDto("wjdrbs966@gmail.com", "KAKAO");
        userService.test(userDto);
        return "test";
    }
}
