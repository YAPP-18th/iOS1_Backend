package com.yapp.ios1.dto.bucket;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 버킷 수정할 경우 데이터베이스에서 가져올 필드
 *
 * created by ayoung 2021/05/21
 */
@Getter
@Setter
@ToString
public class BucketCompareDto {
    private Long id;
    private Long userId;
    private String bucketName;
    private String bucketState;
    private int categoryId;
    private String endDate;
    private String content;

//    private List<String> imageUrlList;
//    private List<TagDto> tagList;
}
