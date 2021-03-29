package com.yapp.ios1.controller;


import com.yapp.ios1.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * created by ayoung 2021/03/29
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
public class S3Controller {

    private final S3Service s3Service;

    /**
     * S3 파일 업로드를 위한 임시 코드 (삭제 예정)
     */
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        return s3Service.upload(multipartFile);
    }
}