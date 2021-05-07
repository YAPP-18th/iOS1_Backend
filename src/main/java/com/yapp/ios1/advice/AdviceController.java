package com.yapp.ios1.advice;

import com.yapp.ios1.common.ResponseMessage;
import com.yapp.ios1.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 * created by jg 2021/04/11
 */
@Slf4j
@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto> tokenException(IllegalArgumentException e) {
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.UNAUTHORIZED, e.getMessage()));
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ResponseDto> sqlException(SQLException e) {
        log.info(e.getMessage());
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.DATABASE_ERROR));
    }
}
