package com.yapp.ios1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.dto.jwt.JwtPayload;
import com.yapp.ios1.dto.jwt.TokenResponseDto;
import com.yapp.ios1.service.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.yapp.ios1.common.ResponseMessage.LOGIN_SUCCESS;

/**
 * created by jg 2021/05/05
 */
@Api(value = "Token")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
public class TokenController {

    private final JwtService jwtService;

    @ApiOperation(value = "서버 토큰 발급 용")
    @GetMapping("/token")
    public String getToken() {
        try {
            return jwtService.createAccessToken(new JwtPayload(1L));
        } catch (JsonProcessingException e) {
            return "에러";
        }
    }

    @ApiOperation(value = "토큰 재발급")
    @PostMapping("/token/refresh")
    public ResponseEntity<ResponseDto> reissueToken(@RequestBody String refreshToken) throws JsonProcessingException {
        ResponseDto response = ResponseDto.of(HttpStatus.OK, LOGIN_SUCCESS, jwtService.reissueToken(refreshToken));
        return ResponseEntity.ok().body(response);
    }
}
