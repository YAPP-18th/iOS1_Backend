package com.yapp.ios1.dto.bucket;

import lombok.Getter;

import java.util.List;

/**
 * created by jg 2021/05/09
 */
@Getter
public class BucketResultDto {
    private List<BucketDto> buckets;
    private int bucketCount;

    public BucketResultDto(List<BucketDto> buckets, int bucketCount) {
        this.buckets = buckets;
        this.bucketCount = bucketCount;
    }
}
