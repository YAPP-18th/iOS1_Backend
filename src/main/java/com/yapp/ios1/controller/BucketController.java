package com.yapp.ios1.controller;

import com.yapp.ios1.common.ResponseMessage;
import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.dto.bucket.BucketRequestDto;
import com.yapp.ios1.service.BucketService;
import com.yapp.ios1.utils.auth.Auth;
import com.yapp.ios1.utils.auth.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * created by jg 2021/05/05
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/buckets")
@Api(tags = "Bucket")
public class BucketController {

    private final BucketService bucketService;

    /**
     * @param bucketState ONGOING(진행 중), EXPECT(예정), COMPLETE(완료), ALL(전체)
     * @INFO categoryId 10 => 전체 조회
     * @INFO sortId = 1(작성 순), sortId = 2(가나다 순)
     * @return BucketResultDto
     */
    @ApiOperation(value = "홈 화면 전체 조회")
    @Auth
    @GetMapping("")
    public ResponseEntity<ResponseDto> home(@RequestParam("bucketState") String bucketState,
                                            @RequestParam("category") String category,
                                            @RequestParam("sort") String sort) {
        Long userId = UserContext.getCurrentUserId();

        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.GET_BUCKET_LIST,
                        bucketService.homeBucketList(bucketState, category, userId, sort)));
    }

    /**
     * @param bucket    버킷 등록 정보
     * @param imageList 버킷 이미지 리스트
     */
    @ApiOperation(
            value = "버킷 등록",
            notes = "포스트맨에서 테스트 가능"
    )
    @Auth
    @PostMapping("")
    public ResponseEntity<ResponseDto> registerBucket(@RequestPart(value = "image", required = false) MultipartFile[] imageList,
                                                      @RequestPart @Valid BucketRequestDto bucket) throws IOException, IllegalArgumentException {
        if (imageList != null) {
            bucket.setImageList(imageList);
        }
        bucket.setUserId(UserContext.getCurrentUserId());
        bucketService.registerBucket(bucket);
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.CREATED, ResponseMessage.REGISTER_BUCKET_SUCCESS));
    }

    /**
     * 버킷 업데이트
     */
    @ApiOperation(
            value = "버킷 수정",
            notes = "포스트맨에서 테스트 가능"
    )
    @Auth
    @PutMapping("/{bucketId}")
    public ResponseEntity<ResponseDto> updateBucket(@PathVariable Long bucketId,
                                                    @RequestParam(value = "content", required = false) String content,
                                                    @RequestParam(value = "endDate", required = false) String endDate,
                                                    @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                                    @RequestParam(value = "bucketName", required = false) String bucketName) {
        if (categoryId == null) {
            categoryId = 0;
        }

        BucketRequestDto bucket = BucketRequestDto.builder()
                .content(content)
                .bucketName(bucketName)
                .endDate(endDate)
                .categoryId(categoryId)
                .build();

        bucketService.updateBucket(bucketId, bucket, UserContext.getCurrentUserId());

        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.REGISTER_BUCKET_SUCCESS));
    }
}
