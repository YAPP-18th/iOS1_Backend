package com.yapp.ios1.dto.bucket;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

/**
 * created by jg 2021/05/05
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class BucketDto {
    private Long id;
    private String bucketName;
    private String content;
    private String createdDate;
    private String endDate;
    private int bucketState;
    private Integer categoryId;
    private boolean isBookmark;
    private boolean isFin;
}
