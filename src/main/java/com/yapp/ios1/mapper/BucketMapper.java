package com.yapp.ios1.mapper;

import com.yapp.ios1.dto.bucket.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * created by jg 2021/05/05
 */
@Mapper
public interface BucketMapper {

    Optional<BucketCompareDto> findByBucketId(Long bucketId);

    void registerBucket(BucketRequestDto registerDto);

    void updateBucket(BucketCompareDto updateDto);

    void saveBucketImageUrlList(@Param("bucketId") Long bucketId, List<String> imageUrlList);

    Optional<TagDto> findByTagName(String tagName);

    void saveBucketAndTag(@Param("bucketId") Long bucketId, @Param("tagId") Long tagId);

    void saveTag(TagDto tag);

    List<BucketDto> findByBucketStateAndCategory(@Param("bucketState") String bucketState,
                                                 @Param("category") String category,
                                                 @Param("userId") Long userId,
                                                 @Param("sort") String sort);

    List<BucketDto> findByUserId(Long userId);

    int findByCategoryId(Long categoryId);

    List<BookmarkDto> findBookmarkListByUserId(@Param("userId") Long userId);

    int getBucketCountByUserId(@Param("userId") Long userId);

    void deleteTagListByBucketId(Long bucketId);

    void saveBucketNameLog(Long bucketId);

    void saveBucketEndDateLog(Long bucketId);
}
