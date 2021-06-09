package com.yapp.ios1.service;

import com.yapp.ios1.controller.dto.bucket.BucketRequestDto;
import com.yapp.ios1.dto.bucket.*;
import com.yapp.ios1.error.exception.bucket.BucketNotFoundException;
import com.yapp.ios1.mapper.BucketMapper;
import com.yapp.ios1.model.bucket.Bookmark;
import com.yapp.ios1.model.bucket.Bucket;
import com.yapp.ios1.model.bucket.BucketTimeline;
import com.yapp.ios1.model.image.Image;
import com.yapp.ios1.model.tag.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * created by jg 2021/05/05
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class BucketService {

    private final BucketMapper bucketMapper;

    public BucketHomeDto getHomeBucketList(int bucketState, int category, Long userId, int sort) {
        List<Bucket> buckets = bucketMapper.findByBucketStateAndCategory(bucketState, category, userId, sort);
        return new BucketHomeDto(
                buckets,
                buckets.size()
        );
    }

    private Bucket findBucketByBucketIdAndUserId(Long bucketId, Long userId) {
        return bucketMapper.findByBucketIdAndUserId(bucketId, userId)
                .orElseThrow(BucketNotFoundException::new);
    }

    private List<Tag> findByBucketTagByBucketId(Long bucketId) {
        return bucketMapper.findByBucketTagByBucketId(bucketId);
    }

    private List<Image> findByBucketImageByBucketId(Long bucketId, Long userId) {
        return bucketMapper.findByBucketImageByBucketId(bucketId, userId);
    }

    private List<BucketTimeline> findByBucketTimelineByBucketId(Long bucketId, Long userId) {
        return bucketMapper.findByBucketTimelineByBucketId(bucketId, userId);
    }

    public BucketDetailDto getBucketOne(Long bucketId, Long userId) {
        return new BucketDetailDto(
                findBucketByBucketIdAndUserId(bucketId, userId),
                findByBucketImageByBucketId(bucketId, userId),
                findByBucketTagByBucketId(bucketId),
                findByBucketTimelineByBucketId(bucketId, userId)
        );
    }

    public List<Bucket> getUserBucketList(Long userId) {
        return bucketMapper.findByUserId(userId);
    }

    @Transactional
    public void registerBucket(BucketRequestDto registerDto) {
        bucketMapper.registerBucket(registerDto);

        Long bucketId = registerDto.getId();
        saveTagList(bucketId, registerDto.getTagList());
        saveImageUrlList(bucketId, registerDto.getImageList());
    }

    @Transactional
    public void updateBucket(Long bucketId, BucketRequestDto updateDto, Long userId) {
        Bucket bucketDto = findBucketByBucketIdAndUserId(bucketId, userId);

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

    public void completeBucket(Long bucketId, Long userId) {
        checkValidBucket(bucketId, userId);
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

    public void setBookmark(Long bucketId, Long userId, boolean isBookmark) {
        checkValidBucket(bucketId, userId);
        bucketMapper.setBookmark(bucketId, isBookmark);
    }

    public void setBucketFile(Long bucketId, Long userId, boolean isFin) {
        checkValidBucket(bucketId, userId);
        bucketMapper.setBucketFin(bucketId, isFin);
    }

    private void checkValidBucket(Long bucketId, Long userId) {
        bucketMapper.findByBucketIdAndUserId(bucketId, userId)
                .orElseThrow(BucketNotFoundException::new);
    }
}