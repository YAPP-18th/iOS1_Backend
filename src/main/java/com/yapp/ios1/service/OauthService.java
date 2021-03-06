package com.yapp.ios1.service;

import com.yapp.ios1.model.user.User;
import com.yapp.ios1.dto.user.UserStatusDto;
import com.yapp.ios1.controller.dto.user.login.SignUpDto;
import com.yapp.ios1.controller.dto.user.social.SocialLoginDto;
import com.yapp.ios1.properties.SocialLoginProperties;
import com.yapp.ios1.service.jwt.JwtService;
import com.yapp.ios1.service.user.UserFindService;
import com.yapp.ios1.service.user.UserService;
import com.yapp.ios1.validator.UserValidator;
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
    private final UserFindService userFindService;
    private final UserValidator userValidator;
    private final JwtService jwtService;
    private final SocialLoginProperties socialLoginProperties;

    @Transactional
    public UserStatusDto getSocialUser(String socialType, SocialLoginDto socialDto) {
        String socialId = socialDto.getSocialId();
        String email = socialDto.getEmail();

        Optional<User> optionalUser = userFindService.findBySocialIdAndSocialType(socialId, socialType);

        if (optionalUser.isEmpty()) {
            if (!email.equals("")) {
                userValidator.checkEmailDuplicate(email);
            }
            return socialSignUp(socialType, socialDto);
        }
        return new UserStatusDto(HttpStatus.OK, jwtService.createTokenResponse(optionalUser.get().getId()));
    }

    private UserStatusDto socialSignUp(String socialType, SocialLoginDto socialDto) {
        SignUpDto signUpDto = SignUpDto.builder()
                .socialType(socialType)
                .email(socialDto.getEmail())
                .password(socialLoginProperties.getKey())
                .socialId(socialDto.getSocialId())
                .deviceToken(socialDto.getDeviceToken())
                .build();
        return new UserStatusDto(HttpStatus.CREATED, userService.signUp(User.of(signUpDto)));
    }
}
