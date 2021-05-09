package com.yapp.ios1.service;

import com.yapp.ios1.dto.bucket.BucketRegisterDto;
import com.yapp.ios1.dto.bucket.TagDto;
import com.yapp.ios1.common.ResponseMessage;
import com.yapp.ios1.dto.bucket.BucketDto;
import com.yapp.ios1.dto.bucket.BucketResultDto;
import com.yapp.ios1.exception.bucket.CategoryNotFoundException;
import com.yapp.ios1.mapper.BucketMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


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
    private final S3Service s3Service;

    public BucketResultDto homeBucketList(String bucketState, Long categoryId, Long userId) {
        if (bucketMapper.findByCategoryId(categoryId) == NOT_FOUND_CATEGORY) {
            throw new CategoryNotFoundException(ResponseMessage.NOT_FOUND_CATEGORY);
        }

        // 버킷 전체, 카테고리
        if (bucketState.equals(BUCKET_LIST_ALL)) {
            List<BucketDto> buckets = bucketMapper.findByUserBucketList(userId, categoryId);
            return new BucketResultDto(
                    buckets,
                    buckets.size()
            );
        }

        // 버킷 상태 선택, 카테고리
        List<BucketDto> buckets = bucketMapper.findByBucketStateAndCategory(bucketState, categoryId, userId);
        return new BucketResultDto(
                buckets,
                buckets.size()
        );
    }

    // 버킷 등록
    public void registerBucket(BucketRegisterDto registerDto) throws IOException, IllegalArgumentException {
        bucketMapper.registerBucket(registerDto); // bucket 저장
        for (TagDto tag : registerDto.getTagList()) {
            Optional<TagDto> optionalTag = bucketMapper.findByTagName(tag.getTagName());
            if (optionalTag.isEmpty()) { // 태그 기존에 없는 경우, tag 저장
                bucketMapper.saveTag(tag);
            } else {
                tag.setId(optionalTag.get().getId());
            }
            bucketMapper.saveBucketAndTag(registerDto.getId(), tag.getId()); // bucket_tag 저장
        }
        List<String> imageUrlList = s3Service.upload(registerDto.getImageList());
        for (String imageUrl : imageUrlList) {
            bucketMapper.saveBucketImageUrl(registerDto.getId(), imageUrl);
        }
    }
}
