package com.yapp.ios1.dto.bucket;

import lombok.Getter;

/**
 * created by jg 2021/05/05
 */
@Getter
public class BucketDto {
    private Long id;
    private String bucket_name;
    private String start_date;
    private String end_date;
    private String bucket_state;
    private int bucketListCount;
}
