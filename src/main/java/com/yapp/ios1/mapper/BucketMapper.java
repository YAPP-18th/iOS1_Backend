package com.yapp.ios1.mapper;

import com.yapp.ios1.dto.bucket.BucketDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * created by jg 2021/05/05
 */
@Mapper
public interface BucketMapper {
    List<BucketDto> findByBucketStateAndCategory(@Param("bucketState") String bucketState,
                                                 @Param("categoryId") Long categoryId,
                                                 @Param("userId") Long userId,
                                                 @Param("sortId") Long sortId);
    int findByCategoryId(Long categoryId);
}
