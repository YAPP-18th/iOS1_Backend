package com.yapp.ios1.controller;

import com.yapp.ios1.common.ResponseMessage;
import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.dto.bucket.BucketRequestDto;
import com.yapp.ios1.service.BucketService;
import com.yapp.ios1.service.S3Service;
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

import static com.yapp.ios1.common.ResponseMessage.NOT_EXIST_IMAGE;
import static com.yapp.ios1.common.ResponseMessage.UPLOAD_IMAGE_SUCCESS;

/**
 * created by jg 2021/05/05
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/buckets")
@Api(tags = "Bucket")
public class BucketController {

    private final BucketService bucketService;
    private final S3Service s3Service;

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
     * @param imageList 버킷 이미지 리스트
     */
    @ApiOperation(
            value = "이미지 업로드",
            notes = "이미지 url 배열 리턴"
    )
    @Auth
    @PostMapping("/images")
    public ResponseEntity<ResponseDto> registerBucketImageList(@RequestParam(value = "image") MultipartFile[] imageList) throws IOException {
        if (imageList == null) {
            throw new IllegalArgumentException(NOT_EXIST_IMAGE);
        }

        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.OK, UPLOAD_IMAGE_SUCCESS, s3Service.upload(imageList)));
    }

    /**
     * @param bucket    버킷 등록 정보
     */
    @ApiOperation(
            value = "버킷 등록"
    )
    @Auth
    @PostMapping("")
    public ResponseEntity<ResponseDto> registerBucket(@RequestBody @Valid BucketRequestDto bucket) throws IllegalArgumentException {
        bucket.setUserId(UserContext.getCurrentUserId());
        bucketService.registerBucket(bucket);
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.CREATED, ResponseMessage.REGISTER_BUCKET_SUCCESS));
    }

    /**
     * 버킷 업데이트
     */
    @ApiOperation(
            value = "버킷 수정"
    )
    @Auth
    @PutMapping("/{bucketId}")
    public ResponseEntity<ResponseDto> updateBucket(@PathVariable Long bucketId, @RequestBody @Valid BucketRequestDto requestDto) {
        bucketService.updateBucket(bucketId, requestDto, UserContext.getCurrentUserId());

        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.REGISTER_BUCKET_SUCCESS));
    }
}
