package com.yapp.ios1.service;

import com.yapp.ios1.dto.bucket.*;
import com.yapp.ios1.exception.bucket.BucketNotFoundException;
import com.yapp.ios1.mapper.BucketMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.yapp.ios1.common.ResponseMessage.BAD_USER;
import static com.yapp.ios1.common.ResponseMessage.NOT_FOUND_BUCKET;

/**
 * created by jg 2021/05/05
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class BucketService {

    private final BucketMapper bucketMapper;
    private final S3Service s3Service;

    public BucketResultDto homeBucketList(String bucketState, String category, Long userId, String sort) {
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
    public void registerBucket(BucketRequestDto registerDto) throws IOException, IllegalArgumentException {
        bucketMapper.registerBucket(registerDto); // bucket 저장

        Long bucketId = registerDto.getId();
        saveTagList(bucketId, registerDto.getTagList());
        saveImageUrlList(bucketId, s3Service.upload(registerDto.getImageList()));
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

        bucketMapper.updateBucket(setUpdateBucketDto(bucketDto, updateDto)); // 버킷 수정

        // 태그 수정
        updateTag(bucketId, updateDto.getTagList());

        // 이미지 수정


        // 버킷 로그 저장
        if (updateDto.getBucketName() != null) {
            bucketMapper.saveBucketNameLog(bucketId);
        }

        if (updateDto.getEndDate() != null) {
            bucketMapper.saveBucketEndDateLog(bucketId);
        }
    }

    // dto 업데이트
    private BucketCompareDto setUpdateBucketDto(BucketCompareDto bucketDto, BucketRequestDto updateDto) {
        if (updateDto.getBucketName() != null) {
            bucketDto.setBucketName(updateDto.getBucketName());
        }

        if (updateDto.getState() != null) {
            bucketDto.setBucketState(updateDto.getState());
        }

        if (updateDto.getContent() != null) {
            bucketDto.setContent(updateDto.getContent());
        }

        if (updateDto.getEndDate() != null) {
            bucketDto.setEndDate(updateDto.getEndDate());
        }

        if (updateDto.getCategoryId() != 0) {
            bucketDto.setCategoryId(updateDto.getCategoryId());
        }

        return bucketDto;
    }

    // 태그 저장
    private void saveTagList(Long bucketId, List<TagDto> tagList) {
        if (tagList != null) {
            for (TagDto tag : tagList) {
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

    // 태그 수정
    private void updateTag(Long bucketId, List<TagDto> tagList) {
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
