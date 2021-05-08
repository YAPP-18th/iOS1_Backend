package com.yapp.ios1.service;

import com.yapp.ios1.dto.bucket.BucketRegisterDto;
import com.yapp.ios1.dto.bucket.TagDto;
import com.yapp.ios1.model.bucket.BucketDto;
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

    private final BucketMapper bucketMapper;
    private final S3Service s3Service;

    public List<BucketDto> homeBucketList(String bucketState, Long userId) {
        if (bucketState.equals(BUCKET_LIST_ALL)) {
            return bucketMapper.findByUserBucketListAll(userId);
        }
        return bucketMapper.findByBucketState(bucketState, userId);
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
