package com.yapp.ios1.dto.bucket;

import com.yapp.ios1.model.bucket.Bucket;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * created by jg 2021/05/09
 * 홈 화면 Dto
 */
@Builder
@Getter
public class BucketHomeDto {
    private List<Bucket> buckets;
    private int bucketCount;
    private boolean isAlarmCheck;
}
