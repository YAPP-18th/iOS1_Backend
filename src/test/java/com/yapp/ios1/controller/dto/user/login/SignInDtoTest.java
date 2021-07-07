package com.yapp.ios1.controller.dto.user.login;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

import static com.yapp.ios1.message.ValidMessage.NOT_VALID_EMAIL;

/**
 * created by ayoung 2021/07/07
 */
class SignInDtoTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    public static void close() {
        factory.close();
    }

    @DisplayName("email에 빈문자열 전송 시 에러 발생")
    @Test
    void 빈문자열_유효성_실패_테스트() {
        // given
        SignInDto signInDto = new SignInDto("", "test");

        // when
        Set<ConstraintViolation<SignInDto>> violations = validator.validate(signInDto);

        // then
        assertThat(violations).isNotEmpty();
        violations
                .forEach(error -> {
                    assertThat(error.getMessage()).isEqualTo("공백일 수 없습니다");
                });
    }

    @DisplayName("이메일 형식 아닌 경우 에러 발생")
    @Test
    void 이메일_형식_유효성_실패_테스트() {
        // given
        SignInDto signInDto = new SignInDto("test", "test");

        // when
        Set<ConstraintViolation<SignInDto>> violations = validator.validate(signInDto);

        // then
        assertThat(violations).isNotEmpty();
        violations
                .forEach(error -> {
                    assertThat(error.getMessage()).isEqualTo(NOT_VALID_EMAIL);
                });
    }

    @DisplayName("비밀번호 빈문자열인 경우 에러 발생")
    @Test
    void 비밀번호_유효성_실패_테스트() {
        // given
        SignInDto signInDto = new SignInDto("test@gmail.com", "");

        // when
        Set<ConstraintViolation<SignInDto>> violations = validator.validate(signInDto);

        // then
        assertThat(violations).isNotEmpty();
        violations
                .forEach(error -> {
                    assertThat(error.getMessage()).isEqualTo("공백일 수 없습니다");
                });
    }


    @DisplayName("유효성 성공")
    @Test
    void 유효성_성공_테스트() {
        // given
        SignInDto signInDto = new SignInDto("test@gmail.com", "test");

        // when
        Set<ConstraintViolation<SignInDto>> violations = validator.validate(signInDto);

        // then
        assertThat(violations).isEmpty();
    }

}