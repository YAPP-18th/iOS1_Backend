package com.yapp.ios1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yapp.ios1.dto.JwtPayload;
import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.dto.user.social.SocialType;
import com.yapp.ios1.dto.user.social.UserCheckDto;
import com.yapp.ios1.dto.user.TokenDto;
import com.yapp.ios1.service.JwtService;
import com.yapp.ios1.service.OauthService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

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

    @PostMapping("")
    public ResponseEntity<ResponseDto> socialLogin(@RequestParam("social_type") String socialType, @RequestBody TokenDto tokenDto) throws JsonProcessingException, SQLException {

        UserCheckDto checkDto;

        switch (socialType) {
            case SocialType.GOOGLE:
                checkDto = oauthService.getGoogleUser(tokenDto.getAccessToken());
                break;
            case SocialType.KAKAO:
                checkDto = oauthService.getKakaoUser(tokenDto.getAccessToken());
                break;
            case SocialType.APPLE:
                checkDto = oauthService.getAppleUser(tokenDto.getAccessToken());
                break;
            default:
                return ResponseEntity.ok(ResponseDto.of(HttpStatus.BAD_REQUEST, "잘못된 소셜 타입입니다."));
        }

        String token = jwtService.createToken(new JwtPayload(checkDto.getUserId()));
        ResponseDto response = ResponseDto.of(HttpStatus.valueOf(checkDto.getStatus()), "액세스 토큰 발급", new TokenDto(token));
        return ResponseEntity.ok(response);
    }
}
