package com.yapp.ios1.dto.search;

import lombok.Getter;

/**
 * created by jg 2021/05/18
 */
@Getter
public class BookMarkSearchDto {
    private Long bucketId;
    private Long userId;
    private String bucketName;
    private String bucketState;
    private String endDate;
    private String userProfileUrl;
}
