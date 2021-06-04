package com.yapp.ios1.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapp.ios1.dto.user.UserDto;
import com.yapp.ios1.dto.user.check.UserCheckDto;
import com.yapp.ios1.dto.user.login.SignUpDto;
import com.yapp.ios1.dto.user.login.social.SocialLoginDto;
import com.yapp.ios1.error.exception.common.JsonWriteException;
import com.yapp.ios1.error.exception.user.EmailDuplicatedException;
import com.yapp.ios1.error.exception.user.SocialTypeNotFoundException;
import com.yapp.ios1.properties.SocialLoginProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.yapp.ios1.dto.user.login.social.SocialType.*;

/**
 * created by ayoung 2021/05/04
 */
@RequiredArgsConstructor
@Service
public class OauthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final SocialLoginProperties socialLoginProperties;

    public UserCheckDto getSocialUser(String socialType, SocialLoginDto socialDto) {
        switch (socialType.toUpperCase()) {
            case "GOOGLE":
                return getGoogleUser(socialDto.getToken());
            case "KAKAO":
                return getKakaoUser(socialDto.getToken());
            case "APPLE":
                return getAppleUser(socialDto.getToken(), socialDto.getEmail());
            default:
                throw new SocialTypeNotFoundException();
        }
    }

    private JsonNode getProfile(String accessToken, String requestUrl) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<MultiValueMap<String, String>> profileRequest = new HttpEntity<>(headers);

        ResponseEntity<String> restResponse = restTemplate.exchange(
                requestUrl,
                HttpMethod.POST,
                profileRequest,
                String.class
        );

        return writeJsonAsJsoNode(restResponse);
    }

    private JsonNode writeJsonAsJsoNode(ResponseEntity<String> restResponse) {
        try {
            return objectMapper.readTree(restResponse.getBody());
        } catch (JsonProcessingException e) {
            throw new JsonWriteException();
        }
    }

    // 구글 로그인
    private UserCheckDto getGoogleUser(String accessToken) {
        JsonNode profile = getProfile(accessToken, socialLoginProperties.getHost().getGoogle());
        String userEmail = profile.get("email").textValue();

        Optional<UserDto> optionalUser = userService.emailCheck(userEmail);
        if (optionalUser.isEmpty()) { // 회원가입 처리
            SignUpDto signUpDto = SignUpDto.builder()
                    .email(userEmail)
                    .socialType(GOOGLE)
                    .password(socialLoginProperties.getKey())
                    .socialId(profile.get("sub").textValue())
                    .build();
            return new UserCheckDto(HttpStatus.CREATED, userService.signUp(UserDto.of(signUpDto)));
        }
        return new UserCheckDto(HttpStatus.OK, optionalUser.get().getId());
    }

    // 카카오 로그인
    private UserCheckDto getKakaoUser(String accessToken) {
        JsonNode profile = getProfile(accessToken, socialLoginProperties.getHost().getKakao());
        String userEmail = profile.get("kakao_account").get("email").textValue();

        Optional<UserDto> optionalUser = userService.emailCheck(userEmail);
        if (optionalUser.isEmpty()) {
            SignUpDto signUpDto = SignUpDto.builder()
                    .email(userEmail)
                    .socialType(KAKAO)
                    .password(socialLoginProperties.getKey())
                    .socialId(profile.get("id").toString())
                    .build();
            return new UserCheckDto(HttpStatus.CREATED, userService.signUp(UserDto.of(signUpDto)));
        }
        return new UserCheckDto(HttpStatus.OK, optionalUser.get().getId());
    }

    // 애플 로그인
    public UserCheckDto getAppleUser(String identityToken, String email) {
        // 이메일 중복 체크
        userService.emailCheck(email)
                .ifPresent(userDto -> { throw new EmailDuplicatedException();});

        String socialId = jwtService.getSubject(identityToken);
        Optional<UserDto> optionalUser = userService.socialIdCheck(socialId);

        if (optionalUser.isEmpty()) {
            SignUpDto signUpDto = SignUpDto.builder()
                    .email(email)
                    .socialType(APPLE)
                    .password(socialLoginProperties.getKey())
                    .socialId(socialId)
                    .build();
            return new UserCheckDto(HttpStatus.CREATED, userService.signUp(UserDto.of(signUpDto))); // 회원가입
        }
        return new UserCheckDto(HttpStatus.OK, optionalUser.get().getId()); // 로그인
    }
}
