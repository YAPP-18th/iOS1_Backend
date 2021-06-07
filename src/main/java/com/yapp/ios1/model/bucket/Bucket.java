package com.yapp.ios1.model.bucket;

import lombok.Getter;

import java.time.LocalDate;

/**
 * created by jg 2021/05/05
 * 버킷 홈, 나의버킷-북마크 검색 모델
 */
@Getter
public class Bucket {
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
