package com.yapp.ios1.enums;

/**
 * created by jg 2021/06/10
 */
public enum BucketStatus {
    BUCKET_WHOLE(1),            // 전체 버킷
    BUCKET_EXPECTED(2),         // 예정 버킷
    BUCKET_ONGOING(3),          // 진행 중 버킷
    BUCKET_COMPLETE(4),         // 완료 버킷
    BUCKET_FAIL(5)              // 실패 버킷
    ;
    private final int bucketStatus;

    BucketStatus(int bucketStatus) {
        this.bucketStatus = bucketStatus;
    }

    public int get() {
        return bucketStatus;
    }
}
