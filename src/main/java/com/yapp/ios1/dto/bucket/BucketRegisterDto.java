package com.yapp.ios1.dto.bucket;

import com.yapp.ios1.utils.validation.BucketConstraint;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * created by ayoung 2021/05/08
 */
@Getter
@Builder
public class BucketRegisterDto {

    private Long id;
    private Long userId;
    private String bucketName;
    @BucketConstraint
    private String state;
    private int categoryId;
    private String startDate;
    private String endDate;
    private String content;
    private MultipartFile[] imageList;
    private List<TagDto> tagList;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setImageList(MultipartFile[] imageList) {
        this.imageList = imageList;
    }

}