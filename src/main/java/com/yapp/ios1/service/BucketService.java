package com.yapp.ios1.service;

import com.yapp.ios1.dto.bucket.*;
import com.yapp.ios1.exception.bucket.BucketNotFoundException;
import com.yapp.ios1.exception.bucket.FailedUpdateException;
import com.yapp.ios1.mapper.BucketMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.yapp.ios1.common.ResponseMessage.*;

/**
 * created by jg 2021/05/05
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class BucketService {

    private final BucketMapper bucketMapper;

    public BucketResultDto homeBucketList(int bucketState, int category, Long userId, int sort) {
        List<BucketDto> buckets = bucketMapper.findByBucketStateAndCategory(bucketState, category, userId, sort);
        return new BucketResultDto(
                buckets,
                buckets.size()
        );
    }

    public List<BucketDto> getUserBucketList(Long userId) {
        return bucketMapper.findByUserId(userId);
    }

    // 버킷 등록
    @Transactional
    public void registerBucket(BucketRequestDto registerDto) throws IllegalArgumentException {
        bucketMapper.registerBucket(registerDto); // bucket 저장

        Long bucketId = registerDto.getId();
        saveTagList(bucketId, registerDto.getTagList());
        saveImageUrlList(bucketId, registerDto.getImageList());
    }

    // 버킷 수정
    @Transactional
    public void updateBucket(Long bucketId, BucketRequestDto updateDto, Long userId) throws IllegalArgumentException {
        Optional<BucketCompareDto> optional = bucketMapper.findByBucketId(bucketId);

        if (optional.isEmpty()) {
            throw new BucketNotFoundException(NOT_FOUND_BUCKET);
        }

        BucketCompareDto bucketDto = optional.get();

        if (!bucketDto.getUserId().equals(userId)) {
            throw new IllegalArgumentException(BAD_USER);
        }
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
        if (tagList != null) {
            for (String tagName : tagList) {
                TagDto tag = new TagDto(tagName);
                Optional<TagDto> optionalTag = bucketMapper.findByTagName(tag.getTagName());
                if (optionalTag.isEmpty()) { // 태그 기존에 없는 경우, tag 저장
                    bucketMapper.saveTag(tag);
                } else {
                    tag.setId(optionalTag.get().getId());
                }
                bucketMapper.saveBucketAndTag(bucketId, tag.getId()); // bucket_tag 저장
            }
        }
    }

    public void completeBucket(Long bucketId, Long userId) {
        int complete = bucketMapper.completeBucket(bucketId, userId);
        if (complete == 0) {
            throw new FailedUpdateException(UPDATE_BUCKET_FAIL);
        }
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
    private void updateImageUrlList(Long bucketId, List<String> imageUrlList) {
        // 이미지 제거
        bucketMapper.deleteImageListByBucketId(bucketId);
        // 이미지 INSERT
        saveImageUrlList(bucketId, imageUrlList);
    }

    public List<BookmarkDto> getBookmarkList(Long userId) {
        return bucketMapper.findBookmarkListByUserId(userId);
    }

    /**
     * 버킷 수 가져오기
     *
     * @param userId 사용자 id
     */
    public int getBucketCountByUserId(Long userId) {
        return bucketMapper.getBucketCountByUserId(userId);
    }
}
