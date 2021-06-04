package com.yapp.ios1.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.yapp.ios1.error.exception.aws.S3FileIOException;
import com.yapp.ios1.error.exception.user.EmailDuplicatedException;
import com.yapp.ios1.properties.S3Properties;
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
    private final S3Properties s3Properties;

    public List<String> upload(MultipartFile[] multipartFileList) {
        List<String> imageUrlList = new ArrayList<>();
        if (multipartFileList != null) {
            for (MultipartFile multipartFile : multipartFileList) {
                imageUrlList.add(upload(multipartFile));
            }
        }
        return imageUrlList;
    }

    public String upload(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();

            ObjectMetadata objMeta = new ObjectMetadata();

            byte[] bytes = IOUtils.toByteArray(file.getInputStream());
            objMeta.setContentLength(bytes.length);

            ByteArrayInputStream byteArrayIs = new ByteArrayInputStream(bytes);

            s3Client.putObject(new PutObjectRequest(s3Properties.getBucketName(), s3Properties.getDirectoryName() + fileName, byteArrayIs, objMeta)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            return s3Client.getUrl(s3Properties.getBucketName(), s3Properties.getDirectoryName() + fileName).toString();
        } catch (IOException e) {
            throw new S3FileIOException();
        }
    }
}

