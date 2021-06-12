package com.yapp.ios1.controller;

import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.dto.jwt.JwtPayload;
import com.yapp.ios1.service.JwtService;
import com.yapp.ios1.service.JwtIssueService;
import com.yapp.ios1.annotation.ReAuth;
import com.yapp.ios1.aop.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.yapp.ios1.message.ResponseMessage.LOGIN_SUCCESS;

/**
 * created by jg 2021/05/05
 */
@Api(tags = "Token")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
public class TokenController {

    private final JwtService jwtService;
    private final JwtIssueService jwtIssueService;

    @ApiOperation(value = "서버 토큰 발급 용")
    @GetMapping("/token")
    public String getToken() {
        return jwtIssueService.createAccessToken(new JwtPayload(157L));
    }

    @ApiOperation(value = "토큰 재발급")
    @ReAuth
    @PostMapping("/token/refresh")
    public ResponseEntity<ResponseDto> reissueToken() {
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, LOGIN_SUCCESS, jwtService.createTokenResponse(UserContext.getCurrentUserId())));
    }
}
