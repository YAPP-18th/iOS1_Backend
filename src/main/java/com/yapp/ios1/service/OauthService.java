package com.yapp.ios1.service;

import com.yapp.ios1.dto.user.UserDto;
import com.yapp.ios1.dto.user.check.UserCheckDto;
import com.yapp.ios1.controller.dto.user.login.SignUpDto;
import com.yapp.ios1.controller.dto.user.social.SocialLoginDto;
import com.yapp.ios1.properties.SocialLoginProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * created by ayoung 2021/05/04
 */
@RequiredArgsConstructor
@Service
public class OauthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final SocialLoginProperties socialLoginProperties;

    @Transactional
    public UserCheckDto getSocialUser(String socialType, SocialLoginDto socialDto) {
        String socialId = socialDto.getSocialId();
        String email = socialDto.getEmail();

        Optional<UserDto> optionalUser = userService.findBySocialIdAndSocialType(socialId, socialType);

        if (optionalUser.isEmpty()) {
            userService.emailCheck(email);
            return socialSignUp(socialId, email, socialType);
        }
        return new UserCheckDto(HttpStatus.OK, jwtService.createTokenResponse(optionalUser.get().getId()));
    }

    private UserCheckDto socialSignUp(String socialId, String email, String socialType) {
        SignUpDto signUpDto = SignUpDto.builder()
                .email(email)
                .socialType(socialType)
                .password(socialLoginProperties.getKey())
                .socialId(socialId)
                .build();
        return new UserCheckDto(HttpStatus.CREATED, userService.signUp(UserDto.of(signUpDto)));
    }
}
