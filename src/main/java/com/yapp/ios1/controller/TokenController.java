package com.yapp.ios1.controller;

import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.dto.jwt.JwtPayload;
import com.yapp.ios1.service.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.yapp.ios1.common.ResponseMessage.LOGIN_SUCCESS;

/**
 * created by jg 2021/05/05
 */
@Api(tags = "Token")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
public class TokenController {

    private final JwtService jwtService;

    @ApiOperation(value = "서버 토큰 발급 용")
    @GetMapping("/token")
    public String getToken() {
        return jwtService.createAccessToken(new JwtPayload(1L));
    }

    @ApiOperation(value = "토큰 재발급")
    @PostMapping("/token/refresh")
    public ResponseEntity<ResponseDto> reissueToken(@RequestHeader String refreshToken) {
        ResponseDto response = ResponseDto.of(HttpStatus.OK, LOGIN_SUCCESS, jwtService.reissueToken(refreshToken));
        return ResponseEntity.ok(response);
    }
}
