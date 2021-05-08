package com.yapp.ios1.service;

import com.yapp.ios1.common.ResponseMessage;
import com.yapp.ios1.dto.bucket.BucketDto;
import com.yapp.ios1.dto.bucket.BucketResultDto;
import com.yapp.ios1.exception.bucket.CategoryNotFoundException;
import com.yapp.ios1.mapper.BucketMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * created by jg 2021/05/05
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class BucketService {

    private static final String BUCKET_LIST_ALL = "ALL";
    private static final int NOT_FOUND_CATEGORY = 0;

    private final BucketMapper bucketMapper;

    public BucketResultDto homeBucketList(String bucketState, Long categoryId, Long userId) {
        if (bucketMapper.findByCategoryId(categoryId) == NOT_FOUND_CATEGORY) {
            throw new CategoryNotFoundException(ResponseMessage.NOT_FOUND_CATEGORY);
        }

        // 버킷 전체, 카테고리
        if (bucketState.equals(BUCKET_LIST_ALL)) {
            return new BucketResultDto(
                    bucketMapper.findByUserBucketList(userId, categoryId),
                    bucketMapper.findByUserBucketListCount(userId, categoryId)
            );
        }

        // 버킷 상태 선택, 카테고리
        return new BucketResultDto(
                bucketMapper.findByBucketStateAndCategory(bucketState, categoryId, userId),
                bucketMapper.findByBucketStateAndCategoryCount(bucketState, categoryId, userId)
        );
    }
}
