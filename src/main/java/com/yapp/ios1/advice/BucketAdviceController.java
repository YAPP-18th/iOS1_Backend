package com.yapp.ios1.advice;

import com.yapp.ios1.common.ResponseMessage;
import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.exception.bucket.CategoryNotFoundException;
import com.yapp.ios1.exception.bucket.SortNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * created by jg 2021/05/09
 */
@RestControllerAdvice
public class BucketAdviceController {

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ResponseDto> categoryNotFoundException() {
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.BAD_REQUEST, ResponseMessage.NOT_FOUND_CATEGORY));
    }

    @ExceptionHandler(SortNotFoundException.class)
    public ResponseEntity<ResponseDto> sortNotFoundException() {
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.BAD_REQUEST, ResponseMessage.NOT_FOUND_SORT));
    }
}
