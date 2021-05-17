package com.yapp.ios1.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapp.ios1.dto.user.SignUpDto;
import com.yapp.ios1.dto.user.social.AppleRequestDto;
import com.yapp.ios1.dto.user.social.SocialType;
import com.yapp.ios1.dto.user.social.UserCheckDto;
import com.yapp.ios1.dto.user.UserDto;
import com.yapp.ios1.exception.user.UserDuplicatedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Optional;

import static com.yapp.ios1.common.ResponseMessage.*;
import static com.yapp.ios1.dto.user.social.SocialType.*;

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

    public UserCheckDto getSocialUser(String socialType, String accessToken) throws JsonProcessingException, SQLException {
        try {
            switch (SocialType.valueOf(socialType.toUpperCase())) {
                case GOOGLE:
                    return getGoogleUser(accessToken);
                case KAKAO:
                    return getKakaoUser(accessToken);
                default:
                    return null;
            }
        } catch (IllegalArgumentException e) {
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
    private UserCheckDto getGoogleUser(String accessToken) throws JsonProcessingException, SQLException, HttpClientErrorException {
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
    private UserCheckDto getKakaoUser(String accessToken) throws JsonProcessingException, SQLException {
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
    public UserCheckDto getAppleUser(AppleRequestDto appleUser) throws ParseException, SQLException {

        if (!jwtService.getSubject(appleUser.getIdentityToken()).equals(appleUser.getUserIdentity())) {
            throw new IllegalArgumentException(SOCIAL_LOGIN_ERROR);
        }

        String socialId = appleUser.getUserIdentity();
        Optional<UserDto> optionalUser = userService.socialIdCheck(socialId);

        if (optionalUser.isEmpty()) {
            if (userService.emailCheck(appleUser.getEmail()).isPresent()) { // 이메일 중복 확인
                throw new UserDuplicatedException(EXIST_USER);
            }
            SignUpDto signUpDto = SignUpDto.builder()
                    .email(appleUser.getEmail())
                    .socialType(APPLE)
                    .password(BUOK_KEY)
                    .socialId(appleUser.getUserIdentity())
                    .build();
            return new UserCheckDto(HttpStatus.CREATED, userService.signUp(UserDto.of(signUpDto))); // 회원가입
        }
        return new UserCheckDto(HttpStatus.OK, optionalUser.get().getId()); // 로그인
    }
}
