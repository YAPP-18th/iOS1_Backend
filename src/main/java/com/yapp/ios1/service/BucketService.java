package com.yapp.ios1.service;

import com.yapp.ios1.controller.dto.bucket.BucketRequestDto;
import com.yapp.ios1.dto.bucket.*;
import com.yapp.ios1.error.exception.bucket.BucketNotFoundException;
import com.yapp.ios1.error.exception.user.UserAuthenticationException;
import com.yapp.ios1.mapper.BucketMapper;
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

    public BucketResultDto getHomeBucketList(int bucketState, int category, Long userId, int sort) {
        List<BucketDto> buckets = bucketMapper.findByBucketStateAndCategory(bucketState, category, userId, sort);
        return new BucketResultDto(
                buckets,
                buckets.size()
        );
    }

    private BucketDto findBucketByBucketId(Long bucketId, Long userId) {
        return bucketMapper.findByBucketId(bucketId, userId)
                .orElseThrow(BucketNotFoundException::new);
    }

    private List<TagDto> findByBucketTagByBucketId(Long bucketId) {
        return bucketMapper.findByBucketTagByBucketId(bucketId);
    }

    private List<ImagesDto> findByBucketImageByBucketId(Long bucketId, Long userId) {
        return bucketMapper.findByBucketImageByBucketId(bucketId, userId);
    }

    private List<BucketTimelineDto> findByBucketTimelineByBucketId(Long bucketId, Long userId) {
        return bucketMapper.findByBucketTimelineByBucketId(bucketId, userId);
    }

    public BucketDetailDto getBucketOne(Long bucketId, Long userId) {
        return new BucketDetailDto(
                findBucketByBucketId(bucketId, userId),
                findByBucketImageByBucketId(bucketId, userId),
                findByBucketTagByBucketId(bucketId),
                findByBucketTimelineByBucketId(bucketId, userId)
        );
    }

    public List<BucketDto> getUserBucketList(Long userId) {
        return bucketMapper.findByUserId(userId);
    }

    @Transactional
    public void registerBucket(BucketRequestDto registerDto) throws IllegalArgumentException {
        bucketMapper.registerBucket(registerDto); // bucket 저장

        Long bucketId = registerDto.getId();
        saveTagList(bucketId, registerDto.getTagList());
        saveImageUrlList(bucketId, registerDto.getImageList());
    }

    // 버킷 수정
    @Transactional
    public void updateBucket(Long bucketId, BucketRequestDto updateDto, Long userId) {
        BucketDto bucketDto = findBucketByBucketId(bucketId, userId);

        updateDto.setId(bucketId);
        bucketMapper.updateBucket(updateDto); // 버킷 수정

        // 태그 수정
        updateTag(bucketId, updateDto.getTagList());

        // 이미지 수정
        updateImageUrlList(bucketId, updateDto.getImageList());

        // 버킷 로그 저장
        if (!updateDto.getBucketName().equals(bucketDto.getBucketName())) {
            bucketMapper.saveBucketNameLog(bucketId);
        }

        if (!updateDto.getEndDate().equals(bucketDto.getEndDate())) {
            bucketMapper.saveBucketEndDateLog(bucketId);
        }
    }

    // 태그 저장
    private void saveTagList(Long bucketId, List<String> tagList) {
        bucketMapper.saveTagList(tagList);
        bucketMapper.saveBucketIdAndTagId(bucketId, tagList);
    }

    public void completeBucket(Long bucketId, Long userId) {
        checkValidBucket(bucketId);
        bucketMapper.completeBucket(bucketId, userId);
    }

    // 태그 수정
    private void updateTag(Long bucketId, List<String> tagList) {
        // 태그 제거
        bucketMapper.deleteTagListByBucketId(bucketId);
        // 태그 INSERT
        saveTagList(bucketId, tagList);
    }

    // 이미지 url 저장
    private void saveImageUrlList(Long bucketId, List<String> imageUrlList) {
        if (!imageUrlList.isEmpty()) {
            bucketMapper.saveBucketImageUrlList(bucketId, imageUrlList);
        }
    }

    // 이미지 url 수정
    // TODO 리팩터링
    private void updateImageUrlList(Long bucketId, List<String> imageUrlList) {
        // 이미지 제거
        bucketMapper.deleteImageListByBucketId(bucketId);
        // 이미지 INSERT
        saveImageUrlList(bucketId, imageUrlList);
    }

    public List<BookmarkDto> getBookmarkList(Long userId) {
        return bucketMapper.findBookmarkListByUserId(userId);
    }

    public int getBucketCountByUserId(Long userId) {
        return bucketMapper.getBucketCountByUserId(userId);
    }

    // 북마크 추가
    public void setBookmark(Long bucketId, Long userId, boolean isBookmark) {
        checkValidBucket(bucketId);
        bucketMapper.setBookmark(bucketId, userId, isBookmark);
    }

    private void checkValidBucket(Long bucketId) {
        bucketMapper.findUserIdByBucketId(bucketId)
                .orElseThrow(BucketNotFoundException::new);
    }

    public void setBucketFile(Long bucketId, Long userId, boolean isFin) {
        checkValidBucket(bucketId);
        bucketMapper.setBucketFin(bucketId, userId, isFin);
    }
}