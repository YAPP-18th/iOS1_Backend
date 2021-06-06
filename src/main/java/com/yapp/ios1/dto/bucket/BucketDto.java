package com.yapp.ios1.dto.bucket;

import lombok.Getter;

/**
 * created by jg 2021/05/05
 */
@Getter
public class BucketDto {
    private Long id;
    private Long userId;
    private String bucketName;
    private String content;
    private String createdDate;
    private String endDate;
    private int bucketState;
    private Integer categoryId;
    private boolean isBookmark;
    private boolean isFin;
    private String userProfileUrl;
}
