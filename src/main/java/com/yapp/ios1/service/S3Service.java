package com.yapp.ios1.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * created by ayoung 2021/03/29
 *
 * S3 업로드
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class S3Service {

    private final AmazonS3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${buok.s3.dir.bucket}")
    private String bucketDir;

    public List<String> upload(MultipartFile[] multipartFileList) throws IOException {
        List<String> imageUrlList = new ArrayList<>();
        if (multipartFileList != null) {
            for (MultipartFile multipartFile : multipartFileList) {
                imageUrlList.add(upload(multipartFile, bucketDir));
            }
        }
        return imageUrlList;
    }

    public String upload(MultipartFile file, String dirName) throws IOException {
        String fileName = file.getOriginalFilename();

        ObjectMetadata objMeta = new ObjectMetadata();

        byte[] bytes = IOUtils.toByteArray(file.getInputStream());
        objMeta.setContentLength(bytes.length);

        ByteArrayInputStream byteArrayIs = new ByteArrayInputStream(bytes);

        s3Client.putObject(new PutObjectRequest(bucket, dirName + fileName, byteArrayIs, objMeta)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return s3Client.getUrl(bucket, fileName).toString();
    }
}

