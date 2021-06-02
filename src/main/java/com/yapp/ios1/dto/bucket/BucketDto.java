package com.yapp.ios1.dto.bucket;

import lombok.Getter;

/**
 * created by jg 2021/05/05
 */
@Getter
public class BucketDto {
    private Long id;
    private String bucketName;
    private String createdDate;
    private String endDate;
    private int bucketState;
    private int categoryId;
}
