package com.yapp.ios1.service;

import com.yapp.ios1.controller.dto.bucket.BucketRequestDto;
import com.yapp.ios1.dto.bucket.*;
import com.yapp.ios1.error.exception.bucket.BucketNotFoundException;
import com.yapp.ios1.error.exception.user.UserNotFoundException;
import com.yapp.ios1.mapper.BucketMapper;
import com.yapp.ios1.mapper.UserMapper;
import com.yapp.ios1.model.bookmark.Bookmark;
import com.yapp.ios1.model.bucket.Bucket;
import com.yapp.ios1.model.bucket.BucketTimeline;
import com.yapp.ios1.model.image.Image;
import com.yapp.ios1.model.tag.Tag;
import com.yapp.ios1.model.user.User;
import com.yapp.ios1.validator.BucketValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * created by jg 2021/05/05
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class BucketService {

    private final BucketMapper bucketMapper;
    private final BucketValidator bucketValidator;
    private final UserMapper userMapper;

    public BucketHomeDto getHomeBucketList(int bucketState, int category, Long userId, int sort) {
        List<Bucket> buckets = bucketMapper.findByBucketStateAndCategory(bucketState, category, userId, sort);
        return BucketHomeDto.builder()
                .buckets(buckets)
                .bucketCount(buckets.size())
                .isAlarmCheck(userMapper.alarmCheckStatus(userId))
                .build();
    }

    private Bucket getBucket(Long bucketId, Long userId) {
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

    public BucketDetailDto getBucketOne(Long bucketId, Long userId) {
        return new BucketDetailDto(
                getBucket(bucketId, userId),
                getBucketImage(bucketId, userId),
                getBucketTag(bucketId),
                getBucketTimeline(bucketId, userId)
        );
    }

    public List<Bucket> getUserBucketList(Long userId) {
        return bucketMapper.findByUserId(userId);
    }

    @Transactional
    public void saveBucket(BucketRequestDto registerDto) {
        bucketMapper.registerBucket(registerDto);

        Long bucketId = registerDto.getId();
        saveTagList(bucketId, registerDto.getTagList());
        saveImageUrlList(bucketId, registerDto.getImageList());
    }

    @Transactional
    public void updateBucket(Long bucketId, BucketRequestDto updateDto, Long userId) {
        Bucket bucketDto = getBucket(bucketId, userId);

        updateDto.setId(bucketId);
        bucketMapper.updateBucket(updateDto);

        updateTag(bucketId, updateDto.getTagList());
        updateImageUrlList(bucketId, updateDto.getImageList());

        if (!updateDto.getBucketName().equals(bucketDto.getBucketName())) {
            bucketMapper.saveBucketNameLog(bucketId);
        }

        if (!updateDto.getEndDate().equals(bucketDto.getEndDate())) {
            bucketMapper.saveBucketEndDateLog(bucketId);
        }
    }

    private void saveTagList(Long bucketId, List<String> tagList) {
        bucketMapper.saveTagList(tagList);
        bucketMapper.saveBucketIdAndTagId(bucketId, tagList);
    }

    // TODO 검증 로직 Bucket AOP로 해볼지 고민해보기
    public void completeBucket(Long bucketId, Long userId) {
        bucketValidator.checkValidBucket(bucketId, userId);
        bucketMapper.completeBucket(bucketId);
    }

    private void updateTag(Long bucketId, List<String> tagList) {
        bucketMapper.deleteTagListByBucketId(bucketId);
        saveTagList(bucketId, tagList);
    }

    private void saveImageUrlList(Long bucketId, List<String> imageUrlList) {
        if (!imageUrlList.isEmpty()) {
            bucketMapper.saveBucketImageUrlList(bucketId, imageUrlList);
        }
    }

    private void updateImageUrlList(Long bucketId, List<String> imageUrlList) {
        bucketMapper.deleteImageListByBucketId(bucketId);
        saveImageUrlList(bucketId, imageUrlList);
    }

    public List<Bookmark> getBookmarkList(Long userId) {
        return bucketMapper.findBookmarkListByUserId(userId);
    }

    public int getBucketCountByUserId(Long userId) {
        return bucketMapper.getBucketCountByUserId(userId);
    }

    // TODO 모든 버킷마다 검증하는 메소드가 들어가는데 이거를 AOP 로 빼던가 해보아도 좋을 거 같음 (얘기 해보기)
    public void setBookmark(Long bucketId, Long userId, boolean isBookmark) {
        bucketValidator.checkValidBucket(bucketId, userId);
        bucketMapper.setBookmark(bucketId, isBookmark);
    }

    public void setBucketFile(Long bucketId, Long userId, boolean isFin) {
        bucketValidator.checkValidBucket(bucketId, userId);
        bucketMapper.setBucketFin(bucketId, isFin);
    }

    @Transactional
    public void updateBucketState(Long userId, Long bucketId, int bucketStateId) {
        bucketValidator.checkValidBucketStateId(bucketStateId);
        bucketValidator.checkValidBucket(bucketId, userId);
        bucketMapper.updateBucketState(bucketId, userId, bucketStateId);
    }
}