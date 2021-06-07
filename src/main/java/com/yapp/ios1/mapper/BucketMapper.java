package com.yapp.ios1.mapper;

import com.yapp.ios1.controller.dto.bucket.BucketRequestDto;
import com.yapp.ios1.dto.bucket.*;
import com.yapp.ios1.model.bucket.Bucket;
import com.yapp.ios1.model.image.Image;
import com.yapp.ios1.model.tag.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * created by jg 2021/05/05
 */
@Mapper
public interface BucketMapper {

    Optional<Bucket> findByBucketId(Long bucketId, Long userId);

    void registerBucket(BucketRequestDto registerDto);

    void updateBucket(BucketRequestDto updateDto);

    void saveBucketImageUrlList(@Param("bucketId") Long bucketId, List<String> imageUrlList);

    void saveBucketIdAndTagId(@Param("bucketId") Long bucketId, List<String> tagList);

    void saveTagList(List<String> tagList);

    List<Bucket> findByBucketStateAndCategory(@Param("bucketState") int bucketState,
                                              @Param("category") int category,
                                              @Param("userId") Long userId,
                                              @Param("sort") int sort);

    List<Bucket> findByUserId(Long userId);

    List<BookmarkDto> findBookmarkListByUserId(@Param("userId") Long userId);

    int getBucketCountByUserId(@Param("userId") Long userId);

    void deleteTagListByBucketId(Long bucketId);

    void deleteImageListByBucketId(Long bucketId);

    void saveBucketNameLog(Long bucketId);

    void saveBucketEndDateLog(Long bucketId);

    void completeBucket(Long bucketId, Long userId);

    Optional<BucketCheckDto> findUserIdByBucketId(Long bucketId);

    void setBookmark(Long bucketId, Long userId, boolean isBookmark);

    List<Tag> findByBucketTagByBucketId(Long bucketId);

    List<Image> findByBucketImageByBucketId(Long bucketId, Long userId);

    List<Bucket.BucketTimelineDto> findByBucketTimelineByBucketId(Long bucketId, Long userId);

    void setBucketFin(Long bucketId, Long userId, boolean isFin);
}
