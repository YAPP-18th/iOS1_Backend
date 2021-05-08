package com.yapp.ios1.service;

import com.yapp.ios1.common.ResponseMessage;
import com.yapp.ios1.dto.bucket.BucketDto;
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

    public List<BucketDto> homeBucketList(String bucketState, Long categoryId, Long userId) {
        if (bucketMapper.findByCategoryId(categoryId) == NOT_FOUND_CATEGORY) {
            throw new CategoryNotFoundException(ResponseMessage.NOT_FOUND_CATEGORY);
        }

        if (bucketState.equals(BUCKET_LIST_ALL)) {
            return bucketMapper.findByUserBucketListAll(userId);
        }

        return bucketMapper.findByBucketStateAndCategory(bucketState, categoryId, userId);
    }


}
