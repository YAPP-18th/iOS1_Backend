package com.yapp.ios1.service.bucket;

import com.yapp.ios1.controller.dto.bucket.BucketRequestDto;
import com.yapp.ios1.mapper.BucketMapper;
import com.yapp.ios1.model.bucket.Bucket;
import com.yapp.ios1.validator.BucketValidator;
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

    private final BucketFindService bucketFindService;
    private final BucketValidator bucketValidator;
    private final BucketMapper bucketMapper;

    @Transactional
    public void saveBucket(BucketRequestDto registerDto) {
        bucketMapper.registerBucket(registerDto);

        Long bucketId = registerDto.getId();
        saveTagList(bucketId, registerDto.getTagList());
        saveImageUrlList(bucketId, registerDto.getImageList());
    }

    @Transactional
    public void updateBucket(Long bucketId, BucketRequestDto updateDto, Long userId) {
        Bucket bucketDto = bucketFindService.getBucket(bucketId, userId);

        updateDto.setId(bucketId);
        bucketMapper.updateBucket(updateDto);

        updateTag(bucketId, updateDto.getTagList());
        updateImageUrlList(bucketId, updateDto.getImageList());

        if (!updateDto.getBucketName().equals(bucketDto.getBucketName())) {
            bucketMapper.saveBucketNameLog(bucketId);
        }

        if (!updateDto.getEndDate().toString().equals(bucketDto.getEndDate())) {
            bucketMapper.saveBucketEndDateLog(bucketId);
        }
    }

    private void saveTagList(Long bucketId, List<String> tagList) {
        if (!tagList.isEmpty()) {
            bucketMapper.saveTagList(tagList);
            bucketMapper.saveBucketIdAndTagId(bucketId, tagList);
        }
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

    // TODO 모든 버킷마다 검증하는 메소드가 들어가는데 이거를 AOP 로 빼던가 해보아도 좋을 거 같음 (얘기 해보기)
    public void saveBookmark(Long bucketId, Long userId, boolean isBookmark) {
        bucketValidator.checkValidBucket(bucketId, userId);
        bucketMapper.setBookmark(bucketId, isBookmark);
    }

    public void setBucketFin(Long bucketId, Long userId, boolean isFin) {
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