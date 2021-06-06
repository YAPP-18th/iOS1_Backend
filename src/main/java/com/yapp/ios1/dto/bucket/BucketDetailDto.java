package com.yapp.ios1.dto.bucket;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * created by jg 2021/06/06
 */
@AllArgsConstructor
@Getter
public class BucketDetailDto {

    private BucketDto bucket;
    private List<ImagesDto> imagess;
    private List<TagDto> tags;
    private List<BucketTimelineDto> bucketTimelines;
}
