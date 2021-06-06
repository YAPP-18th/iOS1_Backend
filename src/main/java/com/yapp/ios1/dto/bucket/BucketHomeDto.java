package com.yapp.ios1.dto.bucket;

import com.yapp.ios1.model.bucket.Bucket;
import lombok.Getter;

import java.util.List;

/**
 * created by jg 2021/05/09
 */
@Getter
public class BucketHomeDto {
    private List<Bucket> buckets;
    private int bucketCount;

    public BucketHomeDto(List<Bucket> buckets, int bucketCount) {
        this.buckets = buckets;
        this.bucketCount = bucketCount;
    }
}
