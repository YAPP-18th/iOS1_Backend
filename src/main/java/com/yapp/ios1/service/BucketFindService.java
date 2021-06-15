package com.yapp.ios1.service;

import com.yapp.ios1.dto.bucket.BucketDetailDto;
import com.yapp.ios1.dto.bucket.BucketHomeDto;
import com.yapp.ios1.error.exception.bucket.BucketNotFoundException;
import com.yapp.ios1.mapper.BucketMapper;
import com.yapp.ios1.mapper.UserMapper;
import com.yapp.ios1.model.bookmark.Bookmark;
import com.yapp.ios1.model.bucket.Bucket;
import com.yapp.ios1.model.bucket.BucketTimeline;
import com.yapp.ios1.model.image.Image;
import com.yapp.ios1.model.tag.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by jg 2021/06/15
 */
@RequiredArgsConstructor
@Service
public class BucketFindService {

    private final BucketMapper bucketMapper;
    private final UserMapper userMapper;

    public BucketHomeDto getHomeBucketList(int bucketState, int category, Long userId, int sort) {
        List<Bucket> buckets = bucketMapper.findByBucketStateAndCategory(bucketState, category, userId, sort);
        return BucketHomeDto.builder()
                .buckets(buckets)
                .bucketCount(buckets.size())
                .isAlarmCheck(userMapper.alarmCheckStatus(userId))
                .build();
    }

    public BucketDetailDto getBucketDetail(Long bucketId, Long userId) {
        return new BucketDetailDto(
                getBucket(bucketId, userId),
                getBucketImage(bucketId, userId),
                getBucketTag(bucketId),
                getBucketTimeline(bucketId, userId)
        );
    }

    public Bucket getBucket(Long bucketId, Long userId) {
        return bucketMapper.findByBucketIdAndUserId(bucketId, userId)
                .orElseThrow(BucketNotFoundException::new);
    }

    private List<Tag> getBucketTag(Long bucketId) {
        return bucketMapper.findByBucketTagByBucketId(bucketId);
    }

    private List<Image> getBucketImage(Long bucketId, Long userId) {
        return bucketMapper.findByBucketImageByBucketId(bucketId, userId);
    }

    private List<BucketTimeline> getBucketTimeline(Long bucketId, Long userId) {
        return bucketMapper.findByBucketTimelineByBucketId(bucketId, userId);
    }

    public List<Bucket> getUserBucketList(Long userId) {
        return bucketMapper.findByUserId(userId);
    }

    public List<Bookmark> getBookmarkList(Long userId) {
        return bucketMapper.findBookmarkListByUserId(userId);
    }

    public int getBucketCountByUserId(Long userId) {
        return bucketMapper.getBucketCountByUserId(userId);
    }
}
