package com.yapp.ios1.controller;

import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.dto.jwt.JwtPayload;
import com.yapp.ios1.dto.user.UserDto;
import com.yapp.ios1.dto.user.check.EmailCodeDto;
import com.yapp.ios1.dto.user.check.EmailDto;
import com.yapp.ios1.service.EmailService;
import com.yapp.ios1.service.JwtService;
import com.yapp.ios1.service.JwtIssueService;
import com.yapp.ios1.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

import static com.yapp.ios1.common.ResponseMessage.*;

/**
 * created by ayoung 2021/05/30
 */
@Api(tags = "Email")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/email")
public class EmailController {

    private final UserService userService;
    private final EmailService emailService;
    private final JwtIssueService jwtIssueService;

    @ApiOperation(
            value = "이메일 인증 코드 전송",
            notes = "입력한 이메일로 인증 코드를 전송합니다."
    )
    @PostMapping("/send")
    public ResponseEntity<ResponseDto> emailAuth(@RequestBody @Valid EmailDto email) {
        Optional<UserDto> user = userService.emailCheck(email.getEmail());
        if (user.isEmpty()) {
            return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.NOT_FOUND, NOT_EXIST_USER));
        }
        emailService.sendSimpleMessage(email.getEmail());

        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, EMAIL_SEND_SUCCESS));
    }

    @ApiOperation(
            value = "인증 코드 검증",
            notes = "인증 성공 시, 토큰을 전달합니다."
    )
    @PostMapping("/verify")
    public ResponseEntity<ResponseDto> verifyCode(@RequestBody EmailCodeDto code) {
        Long userId = emailService.verifyCode(code.getCode());
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.OK, EMAIL_AUTH_SUCCESS, jwtIssueService.createAccessToken(new JwtPayload(userId))));
    }
}