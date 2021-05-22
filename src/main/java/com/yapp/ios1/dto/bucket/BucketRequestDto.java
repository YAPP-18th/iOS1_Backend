package com.yapp.ios1.dto.bucket;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yapp.ios1.utils.validation.BucketConstraint;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * created by ayoung 2021/05/08
 */
@Getter
@Builder
public class BucketRequestDto {

    @JsonIgnore
    private Long id;
    @JsonIgnore
    private Long userId;
    private String bucketName;
    @BucketConstraint
    private String state;
    private int categoryId;
    private String startDate;
    private String endDate;
    private String content;
    private List<String> imageList;
    private List<String> tagList;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setId(Long id) { this.id = id; }
}