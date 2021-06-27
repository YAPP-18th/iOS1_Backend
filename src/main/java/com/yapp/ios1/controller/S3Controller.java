package com.yapp.ios1.controller;

import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.service.S3Service;
import com.yapp.ios1.annotation.Auth;
import com.yapp.ios1.aop.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.yapp.ios1.message.ResponseMessage.NOT_EXIST_IMAGE;
import static com.yapp.ios1.message.ResponseMessage.UPLOAD_IMAGE_SUCCESS;

/**
 * created by ayoung 2021/03/29
 */
@Api(tags = "Image Upload")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
public class S3Controller {

    private final S3Service s3Service;

    @ApiOperation("이미지 url 배열 리턴")
    @Auth
    @PostMapping("/images")
    public ResponseEntity<ResponseDto> registerBucketImageList(@RequestParam(value = "image") MultipartFile[] imageList) {
        if (imageList == null) {
            return ResponseEntity.ok(ResponseDto.of(HttpStatus.BAD_REQUEST, NOT_EXIST_IMAGE));
        }
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, UPLOAD_IMAGE_SUCCESS, s3Service.upload(imageList, UserContext.getCurrentUserId())));
    }
}