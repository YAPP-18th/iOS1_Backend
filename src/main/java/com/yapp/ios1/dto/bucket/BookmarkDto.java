package com.yapp.ios1.dto.bucket;

import lombok.Getter;

/**
 * created by ayoung 2021/05/11
 * // TODO 모델로 이동 (아영님이)
 */
@Getter
public class BookmarkDto {
    private Long id;
    private String bucketName;
    private String endDate;
    private int categoryId;
}
