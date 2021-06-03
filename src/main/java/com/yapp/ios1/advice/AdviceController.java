package com.yapp.ios1.advice;

import com.yapp.ios1.common.ResponseMessage;
import com.yapp.ios1.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.ExecutorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * created by jg 2021/04/11
 */
@Slf4j
@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto> illegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.UNAUTHORIZED, e.getMessage()));
    }

    @ExceptionHandler(ExecutorException.class)
    public ResponseEntity<ResponseDto> executorException(ExecutorException e) {
        log.info(e.getMessage());
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.DATABASE_ERROR));
    }
}
