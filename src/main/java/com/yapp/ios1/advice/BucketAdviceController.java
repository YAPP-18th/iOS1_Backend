package com.yapp.ios1.advice;

import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.exception.bucket.BucketNotFoundException;
import com.yapp.ios1.exception.bucket.FailedUpdateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * created by jg 2021/05/09
 */
@RestControllerAdvice
public class BucketAdviceController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto> validationException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();

        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append(fieldError.getRejectedValue()  + " : ");
            builder.append(fieldError.getDefaultMessage());
        }

        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.BAD_REQUEST, builder.toString()));
    }

    @ExceptionHandler(BucketNotFoundException.class)
    public ResponseEntity<ResponseDto> bucketNotFoundException(BucketNotFoundException e) {
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler(FailedUpdateException.class)
    public ResponseEntity<ResponseDto> failedUpdate(FailedUpdateException e) {
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.BAD_REQUEST, e.getMessage()));
    }
}
