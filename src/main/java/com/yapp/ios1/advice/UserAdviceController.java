package com.yapp.ios1.advice;

import com.yapp.ios1.common.ResponseMessage;
import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.exception.user.EmailNotExistException;
import com.yapp.ios1.exception.user.PasswordNotMatchException;
import com.yapp.ios1.exception.user.UserDuplicatedException;
import com.yapp.ios1.exception.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.text.ParseException;

/**
 * created by jg 2021/05/05
 */
@RestControllerAdvice
public class UserAdviceController {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseDto> userNotFoundException(UserNotFoundException e) {
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.NOT_FOUND, e.getMessage()));
    }

    // 이메일 중복 회원가입
    @ExceptionHandler(UserDuplicatedException.class)
    public ResponseEntity<ResponseDto> userDuplicatedException(UserDuplicatedException e) {
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<ResponseDto> passwordNotMatchException(PasswordNotMatchException e) {
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    // 소셜 로그인
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ResponseDto> socialException() {
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.BAD_REQUEST, ResponseMessage.SOCIAL_LOGIN_ERROR));
    }

    @ExceptionHandler(EmailNotExistException.class)
    public ResponseEntity<ResponseDto> emailNotExistException(EmailNotExistException e) {
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    // JWT Parse 에러
    @ExceptionHandler(ParseException.class) 
    public ResponseEntity<ResponseDto> jwtException() {
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.BAD_REQUEST, ResponseMessage.BAD_JWT));
    }
}
