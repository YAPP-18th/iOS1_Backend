package com.yapp.ios1.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapp.ios1.dto.user.SignUpDto;
import com.yapp.ios1.dto.user.social.GoogleProfileDto;
import com.yapp.ios1.dto.user.social.SocialType;
import com.yapp.ios1.dto.user.social.UserCheckDto;
import com.yapp.ios1.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;

import static com.yapp.ios1.dto.user.social.SocialType.*;

/**
 * created by ayoung 2021/05/04
 */
@RequiredArgsConstructor
@Service
public class OauthService {

    private final UserService userService;
    private final RestTemplate restTemplate;

    @Value("${social.key}")
    private String BUOK_KEY;

    @Value("${social.url.google}")
    private String GOOGLE_REQUEST_URL;

    public UserCheckDto getSocialUser(String socialType, String accessToken) throws JsonProcessingException, SQLException {
        switch (SocialType.valueOf(socialType.toUpperCase(Locale.ROOT))) {
            case GOOGLE:
                return getGoogleUser(accessToken);
            case KAKAO:
                return getKakaoUser(accessToken);
            case APPLE:
                return getAppleUser(accessToken);
            default:
                return null;
        }
    }

    // 구글 로그인
    public UserCheckDto getGoogleUser(String accessToken) throws JsonProcessingException, SQLException, HttpClientErrorException {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<MultiValueMap<String, String>> googleProfileRequest = new HttpEntity<>(headers);

        ResponseEntity<String> restResponse = restTemplate.exchange(
                GOOGLE_REQUEST_URL,
                HttpMethod.POST,
                googleProfileRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        // 구글 프로필 정보
        GoogleProfileDto profile = objectMapper.readValue(restResponse.getBody(), GoogleProfileDto.class);

        Optional<UserDto> optionalUser = userService.emailCheck(profile.getEmail());
        if (optionalUser.isEmpty()) { // 회원가입 처리
            SignUpDto signUpDto = SignUpDto.builder()
                    .email(profile.getEmail())
                    .socialType(GOOGLE)
                    .password(BUOK_KEY)
                    .socialId(profile.getSub())
                    .build();
            return new UserCheckDto(HttpStatus.CREATED, userService.signUp(UserDto.of(signUpDto)));
        }
        return new UserCheckDto(HttpStatus.OK, optionalUser.get().getId());
    }

    // 카카오 로그인
    public UserCheckDto getKakaoUser(String accessToken) {
        return null;
    }

    // 애플 로그인
    public UserCheckDto getAppleUser(String accessToken) {
        return null;
    }
}
