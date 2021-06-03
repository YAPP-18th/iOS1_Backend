package com.yapp.ios1.controller;

import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.dto.user.check.UserCheckDto;
import com.yapp.ios1.dto.user.login.social.SocialLoginDto;
import com.yapp.ios1.service.JwtService;
import com.yapp.ios1.service.OauthService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ResponseDto> socialLogin(@PathVariable("social_type") String socialType,
                                                   @RequestBody SocialLoginDto socialDto) {
        UserCheckDto checkDto = oauthService.getSocialUser(socialType, socialDto);

        ResponseDto response = ResponseDto.of(checkDto.getStatus(), LOGIN_SUCCESS, jwtService.createTokenResponse(checkDto.getUserId()));
        return ResponseEntity.ok(response);
    }
}
