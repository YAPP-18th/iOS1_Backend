package com.yapp.ios1.mapper;

import com.yapp.ios1.dto.bucket.BucketRegisterDto;
import com.yapp.ios1.dto.bucket.TagDto;
import com.yapp.ios1.dto.bucket.BucketDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * created by jg 2021/05/05
 */
@Mapper
public interface BucketMapper {
<<<<<<< HEAD
    List<BucketDto> findByBucketStateAndCategory(@Param("bucketState") String bucketState, @Param("categoryId") Long categoryId, @Param("userId") Long userId);

    List<BucketDto> findByUserBucketListAll(Long userId);

    void registerBucket(BucketRegisterDto registerDto);

    void saveBucketImageUrlList(@Param("bucketId") Long bucketId, List<String> imageUrlList);

    Optional<TagDto> findByTagName(String tagName);

    void saveBucketAndTag(@Param("bucketId") Long bucketId, @Param("tagId") Long tagId);

    void saveTag(TagDto tag);

    List<BucketDto> findByUserBucketList(@Param("userId") Long userId, @Param("categoryId") Long categoryId);

=======
    List<BucketDto> findByBucketStateAndCategory(@Param("bucketState") String bucketState,
                                                 @Param("category") String category,
                                                 @Param("userId") Long userId,
                                                 @Param("sort") String sort);
>>>>>>> 6737b724ef0018ad4cde5c250dd8d0972b309088
    int findByCategoryId(Long categoryId);
}
