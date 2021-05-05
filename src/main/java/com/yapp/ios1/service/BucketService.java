package com.yapp.ios1.service;

import com.yapp.ios1.dto.bucket.BucketDto;
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

    private final BucketMapper bucketMapper;

    public List<BucketDto> homeBucketList(String bucketState, Long userId) {
        if (bucketState.equals("ALL")) {
            return bucketMapper.findByUserBucketListAll(userId);
        }
        return bucketMapper.findByBucketState(bucketState, userId);
    }
}
