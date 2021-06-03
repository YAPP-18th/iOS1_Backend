package com.yapp.ios1.advice;

import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.error.exception.bucket.BucketNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

import static com.yapp.ios1.common.ResponseMessage.NOT_VALID_DATE_TIME;

/**
 * created by jg 2021/05/09
 */
@RestControllerAdvice
public class BucketAdviceController {

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ResponseDto> dateTimeParseException() {
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.BAD_REQUEST, NOT_VALID_DATE_TIME));
    }
}
