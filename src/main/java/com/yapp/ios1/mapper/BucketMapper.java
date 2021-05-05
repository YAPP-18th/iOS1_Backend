package com.yapp.ios1.mapper;

import com.yapp.ios1.dto.bucket.BucketDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * created by jg 2021/05/05
 */
@Mapper
public interface BucketMapper {
    List<BucketDto> findByBucketState(String bucketState, Long userId);

    List<BucketDto> findByUserBucketListAll(Long userId);
}
