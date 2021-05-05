package com.yapp.ios1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yapp.ios1.dto.JwtPayload;
import com.yapp.ios1.service.JwtService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by jg 2021/05/05
 */
@RequiredArgsConstructor
@RestController
public class TokenController {

    private final JwtService jwtService;

    @ApiOperation(value = "서버 토큰 발급 용")
    @GetMapping("/token")
    public String getToken() {
        try {
            return jwtService.createToken(new JwtPayload(1L));
        } catch (JsonProcessingException e) {
            return "에러";
        }
    }
}
