package com.yapp.ios1.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapp.ios1.dto.user.UserDto;
import com.yapp.ios1.dto.user.check.UserCheckDto;
import com.yapp.ios1.dto.user.login.SignUpDto;
import com.yapp.ios1.dto.user.login.social.SocialLoginDto;
import com.yapp.ios1.error.exception.user.EmailDuplicatedException;
import com.yapp.ios1.error.exception.user.EmailNotExistException;
import com.yapp.ios1.error.exception.user.SocialTyeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.util.Optional;

import static com.yapp.ios1.dto.user.login.social.SocialType.*;

/**
 * created by ayoung 2021/05/04
 */
@RequiredArgsConstructor
@Service
public class OauthService {

    // TODO Properties 를 사용하면 없앨 수 있지 않을까 하는 ~ ?
    @Value("${social.key}")
    private String BUOK_KEY;

    @Value("${social.url.google}")
    private String GOOGLE_REQUEST_URL;

    @Value("${social.url.kakao}")
    private String KAKAO_REQUEST_URL;

    private final UserService userService;
    private final JwtService jwtService;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public UserCheckDto getSocialUser(String socialType, SocialLoginDto socialDto) throws ParseException, JsonProcessingException {
        switch (socialType) {
            case "GOOGLE":
                return getGoogleUser(socialDto.getToken());
            case "KAKAO":
                return getKakaoUser(socialDto.getToken());
            case "APPLE":
                return getAppleUser(socialDto.getToken(), socialDto.getEmail());
            default:
                throw new SocialTyeNotFoundException();
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
    private UserCheckDto getGoogleUser(String accessToken) throws JsonProcessingException {
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
                throw new EmailDuplicatedException();
            }
            if (email == null) {
                throw new EmailNotExistException();
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
