package com.yapp.ios1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.dto.jwt.JwtPayload;
import com.yapp.ios1.dto.user.check.EmailCodeDto;
import com.yapp.ios1.dto.user.check.EmailDto;
import com.yapp.ios1.service.EmailService;
import com.yapp.ios1.service.JwtService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.yapp.ios1.common.ResponseMessage.EMAIL_AUTH_SUCCESS;
import static com.yapp.ios1.common.ResponseMessage.EMAIL_SEND_SUCCESS;

/**
 * created by ayoung 2021/05/30
 */
@Api(tags = "Email")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/email")
public class EmailController {

    private final EmailService emailService;
    private final JwtService jwtService;

    @PostMapping("/send") // 이메일 인증 코드 보내기
    public ResponseEntity<ResponseDto> emailAuth(@RequestBody EmailDto email) throws Exception {
        emailService.sendSimpleMessage(email.getEmail());

        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.OK, EMAIL_SEND_SUCCESS));
    }

    @PostMapping("/verify") // 이메일 인증 코드 검증
    public ResponseEntity<ResponseDto> verifyCode(@RequestBody EmailCodeDto code) throws JsonProcessingException {
        Long userId = emailService.verifyCode(code.getCode());
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.OK, EMAIL_AUTH_SUCCESS, jwtService.createAccessToken(new JwtPayload(userId))));
    }
}