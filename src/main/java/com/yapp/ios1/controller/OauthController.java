package com.yapp.ios1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yapp.ios1.dto.JwtPayload;
import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.dto.user.social.AppleRequestDto;
import com.yapp.ios1.dto.user.social.UserCheckDto;
import com.yapp.ios1.dto.user.TokenDto;
import com.yapp.ios1.service.JwtService;
import com.yapp.ios1.service.OauthService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.text.ParseException;

import static com.yapp.ios1.common.ResponseMessage.LOGIN_SUCCESS;

/**
 * created by ayoung 2021/05/04
 */
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v2/social")
@Api(tags = "Social Login")
public class OauthController {

    private final OauthService oauthService;
    private final JwtService jwtService;

    @PostMapping("/{social_type}")
    public ResponseEntity<ResponseDto> socialLogin(@PathVariable("social_type") String socialType, @RequestBody TokenDto tokenDto) throws JsonProcessingException, SQLException {
        UserCheckDto checkDto = oauthService.getSocialUser(socialType, tokenDto.getAccessToken());

        String token = jwtService.createToken(new JwtPayload(checkDto.getUserId()));

        ResponseDto response = ResponseDto.of(checkDto.getStatus(), LOGIN_SUCCESS, new TokenDto(token));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/apple")
    public ResponseEntity<ResponseDto> appleLogin(@RequestBody AppleRequestDto appleUser) throws JsonProcessingException, SQLException, ParseException {
        UserCheckDto checkDto = oauthService.getAppleUser(appleUser);

        String token = jwtService.createToken(new JwtPayload(checkDto.getUserId()));

        ResponseDto response = ResponseDto.of(checkDto.getStatus(), LOGIN_SUCCESS, new TokenDto(token));
        return ResponseEntity.ok(response);
    }
}
