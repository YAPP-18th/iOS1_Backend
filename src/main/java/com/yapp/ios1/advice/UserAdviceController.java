package com.yapp.ios1.advice;

import com.yapp.ios1.common.ResponseMessage;
import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.error.exception.user.EmailNotExistException;
import com.yapp.ios1.error.exception.user.PasswordNotMatchException;
import com.yapp.ios1.error.exception.user.EmailDuplicatedException;
import com.yapp.ios1.error.exception.user.UserNotFoundException;
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


    // 소셜 로그인
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ResponseDto> socialException() {
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.BAD_REQUEST, ResponseMessage.SOCIAL_LOGIN_ERROR));
    }

    // JWT Parse 에러
    @ExceptionHandler(ParseException.class) 
    public ResponseEntity<ResponseDto> jwtException() {
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.BAD_REQUEST, ResponseMessage.BAD_JWT));
    }
}
