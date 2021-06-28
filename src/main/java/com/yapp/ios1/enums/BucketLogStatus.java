package com.yapp.ios1.enums;

/**
 * created by ayoung 2021/06/28
 */
public enum BucketLogStatus {
    BUCKET_NAME_LOG(1),      // 버킷 이름 로그
    BUCKET_END_DATE_LOG(2)      // 버킷 완료 날짜 로그
    ;
    private final int bucketLogStatus;

    BucketLogStatus(int bucketLogStatus) {
        this.bucketLogStatus = bucketLogStatus;
    }

    public int get() {
        return bucketLogStatus;
    }
}
