package com.yapp.ios1.service;

import com.yapp.ios1.dto.bucket.BucketRegisterDto;
import com.yapp.ios1.dto.bucket.TagDto;
import com.yapp.ios1.dto.bucket.BucketDto;
import com.yapp.ios1.dto.bucket.BucketResultDto;
import com.yapp.ios1.mapper.BucketMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final BucketMapper bucketMapper;
    private final S3Service s3Service;

    public BucketResultDto homeBucketList(String bucketState, String category, Long userId, String sort) {
        List<BucketDto> buckets = bucketMapper.findByBucketStateAndCategory(bucketState, category, userId, sort);
        return new BucketResultDto(
                buckets,
                buckets.size()
        );
    }

    // 버킷 등록
    @Transactional
    public void registerBucket(BucketRegisterDto registerDto) throws IOException, IllegalArgumentException {
        bucketMapper.registerBucket(registerDto); // bucket 저장

        Long bucketId = registerDto.getId();
        saveTagList(bucketId, registerDto.getTagList());
        saveImageUrlList(bucketId, s3Service.upload(registerDto.getImageList()));
    }

    // 태그 저장
    public void saveTagList(Long bucketId, List<TagDto> tagList) {
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

    // 이미지 url 저장
    private void saveImageUrlList(Long bucketId, List<String> imageUrlList) {
        bucketMapper.saveBucketImageUrlList(bucketId, imageUrlList);
    }
}
