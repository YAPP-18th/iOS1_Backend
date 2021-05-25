package com.yapp.ios1.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapp.ios1.dto.user.login.SignUpDto;
import com.yapp.ios1.dto.user.login.social.SocialLoginDto;
import com.yapp.ios1.dto.user.login.social.SocialType;
import com.yapp.ios1.dto.user.check.UserCheckDto;
import com.yapp.ios1.dto.user.UserDto;
import com.yapp.ios1.exception.user.EmailNotExistException;
import com.yapp.ios1.exception.user.UserDuplicatedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.util.Optional;

import static com.yapp.ios1.common.ResponseMessage.*;
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

    @Value("${social.key}")
    private String BUOK_KEY;

    @Value("${social.url.google}")
    private String GOOGLE_REQUEST_URL;

    @Value("${social.url.kakao}")
    private String KAKAO_REQUEST_URL;

    public UserCheckDto getSocialUser(String socialType, SocialLoginDto socialDto) throws JsonProcessingException {
        try {
            switch (SocialType.valueOf(socialType.toUpperCase())) {
                case GOOGLE:
                    return getGoogleUser(socialDto.getToken());
                case KAKAO:
                    return getKakaoUser(socialDto.getToken());
                case APPLE:
                    return getAppleUser(socialDto.getToken(), socialDto.getEmail());
                default:
                    return null;
            }
        } catch (IllegalArgumentException | ParseException e) {
            throw new IllegalArgumentException(BAD_SOCIAL_TYPE);
        }
    }

    private JsonNode getProfile(String accessToken, String requestUrl) throws JsonProcessingException {
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

        return objectMapper.readTree(restResponse.getBody());
    }

    // 구글 로그인
    private UserCheckDto getGoogleUser(String accessToken) throws JsonProcessingException, HttpClientErrorException {
        JsonNode profile = getProfile(accessToken, GOOGLE_REQUEST_URL);
        String userEmail = profile.get("email").textValue();

        Optional<UserDto> optionalUser = userService.emailCheck(userEmail);
        if (optionalUser.isEmpty()) { // 회원가입 처리
            SignUpDto signUpDto = SignUpDto.builder()
                    .email(userEmail)
                    .socialType(GOOGLE)
                    .password(BUOK_KEY)
                    .socialId(profile.get("sub").textValue())
                    .build();
            return new UserCheckDto(HttpStatus.CREATED, userService.signUp(UserDto.of(signUpDto)));
        }
        return new UserCheckDto(HttpStatus.OK, optionalUser.get().getId());
    }

    // 카카오 로그인
    private UserCheckDto getKakaoUser(String accessToken) throws JsonProcessingException {
        JsonNode profile = getProfile(accessToken, KAKAO_REQUEST_URL);
        String userEmail = profile.get("kakao_account").get("email").textValue();

        Optional<UserDto> optionalUser = userService.emailCheck(userEmail);
        if (optionalUser.isEmpty()) {
            SignUpDto signUpDto = SignUpDto.builder()
                    .email(userEmail)
                    .socialType(KAKAO)
                    .password(BUOK_KEY)
                    .socialId(profile.get("id").toString())
                    .build();
            return new UserCheckDto(HttpStatus.CREATED, userService.signUp(UserDto.of(signUpDto)));
        }
        return new UserCheckDto(HttpStatus.OK, optionalUser.get().getId());
    }

    // 애플 로그인
    public UserCheckDto getAppleUser(String identityToken, String email) throws ParseException {

        String socialId = jwtService.getSubject(identityToken);
        Optional<UserDto> optionalUser = userService.socialIdCheck(socialId);

        if (optionalUser.isEmpty()) {
            if (userService.emailCheck(email).isPresent()) { // 이메일 중복 확인
                throw new UserDuplicatedException(EXIST_EMAIL);
            }
            if (email == null) {
                throw new EmailNotExistException(NOT_EXIST_EMAIL);
            }
            SignUpDto signUpDto = SignUpDto.builder()
                    .email(email)
                    .socialType(APPLE)
                    .password(BUOK_KEY)
                    .socialId(socialId)
                    .build();
            return new UserCheckDto(HttpStatus.CREATED, userService.signUp(UserDto.of(signUpDto))); // 회원가입
        }
        return new UserCheckDto(HttpStatus.OK, optionalUser.get().getId()); // 로그인
    }
}
