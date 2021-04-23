package com.yapp.ios1.controller;

import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.exception.PasswordNotMatchException;
import com.yapp.ios1.exception.UserDuplicatedException;
import com.yapp.ios1.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 * created by jg 2021/04/11
 */
@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto> tokenException(IllegalArgumentException e) {
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.UNAUTHORIZED, e.getMessage()));
    }

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

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ResponseDto> sqlException(SQLException e) {
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

}
