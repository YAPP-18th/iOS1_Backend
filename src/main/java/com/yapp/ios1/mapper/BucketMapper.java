package com.yapp.ios1.mapper;

import com.yapp.ios1.dto.bucket.BucketDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * created by jg 2021/05/05
 */
@Mapper
public interface BucketMapper {
    List<BucketDto> findByBucketStateAndCategory(String bucketState, Long categoryId, Long userId);

    List<BucketDto> findByUserBucketList(Long userId, Long categoryId);

    int findByCategoryId(Long categoryId);
}
