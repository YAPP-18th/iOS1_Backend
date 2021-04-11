package com.yapp.ios1.controller;

import com.yapp.ios1.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
