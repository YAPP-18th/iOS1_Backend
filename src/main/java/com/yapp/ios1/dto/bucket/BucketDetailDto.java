package com.yapp.ios1.dto.bucket;

import com.yapp.ios1.model.bucket.Bucket;
import com.yapp.ios1.model.image.Image;
import com.yapp.ios1.model.tag.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * created by jg 2021/06/06
 */
@AllArgsConstructor
@Getter
public class BucketDetailDto {

    private Bucket bucket;
    private List<Image> images;
    private List<Tag> tags;
    private List<BucketTimelineDto> bucketTimelines;
}
