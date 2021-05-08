package com.yapp.ios1.dto.bucket;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * created by jg 2021/05/05
 */
@Getter
public class BucketDto {
    private Long id;

    @SerializedName("bucket_name")
    private String bucketName;

    @SerializedName("start_date")
    private String startDate;

    @SerializedName("end_date")
    private String endDate;

    @SerializedName("bucket_state")
    private String bucketState;

    private int bucketListCount;

    @SerializedName("category_name")
    private String categoryName;
}
