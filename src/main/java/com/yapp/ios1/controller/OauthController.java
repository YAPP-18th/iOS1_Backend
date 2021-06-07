package com.yapp.ios1.controller;

import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.dto.user.UserStatusDto;
import com.yapp.ios1.controller.dto.user.social.SocialLoginDto;
import com.yapp.ios1.controller.dto.user.social.SocialType;
import com.yapp.ios1.service.OauthService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PostMapping("/{social_type}")
    public ResponseEntity<ResponseDto> socialLogin(@PathVariable("social_type") SocialType socialType,
                                                   @RequestBody @Valid SocialLoginDto socialDto) {
        UserStatusDto statusDto = oauthService.getSocialUser(socialType.name(), socialDto);
        return ResponseEntity.ok(ResponseDto.of(statusDto.getStatus(), LOGIN_SUCCESS, statusDto.getTokenDto()));
    }
}
