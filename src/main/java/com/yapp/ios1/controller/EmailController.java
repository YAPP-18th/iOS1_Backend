package com.yapp.ios1.controller;

import com.yapp.ios1.controller.dto.email.EmailCodeDto;
import com.yapp.ios1.controller.dto.email.EmailDto;
import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.dto.jwt.JwtPayload;
import com.yapp.ios1.service.EmailService;
import com.yapp.ios1.service.jwt.JwtIssueService;
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

import static com.yapp.ios1.message.ResponseMessage.EMAIL_AUTH_SUCCESS;
import static com.yapp.ios1.message.ResponseMessage.EMAIL_SEND_SUCCESS;

/**
 * created by ayoung 2021/05/30
 */
@Api(tags = "Email")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/email")
public class EmailController {

    private final EmailService emailService;
    private final JwtIssueService jwtIssueService;

    @ApiOperation("이메일 인증 코드 전송")
    @PostMapping("/send")
    public ResponseEntity<ResponseDto> sendEmail(@RequestBody @Valid EmailDto email) {
        emailService.sendEmailMessage(email.getEmail());
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, EMAIL_SEND_SUCCESS));
    }

    @ApiOperation("인증 코드 검증")
    @PostMapping("/verify")
    public ResponseEntity<ResponseDto> verifyCode(@RequestBody @Valid EmailCodeDto code) {
        Long userId = emailService.getUserIdByCode(code.getCode());
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, EMAIL_AUTH_SUCCESS, jwtIssueService.createAccessToken(new JwtPayload(userId))));
    }
}