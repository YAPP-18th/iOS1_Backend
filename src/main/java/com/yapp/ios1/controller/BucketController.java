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
     * @INFO state 1(전체), 2(예정), 3(진행 중), 4(완료)
     * @INFO category 1(전체), 2(여행), 3(취미), 4(소유), 5(재정), 6(건강)
                      7(목표), 8(조직), 9(봉사), 10(기타)
     * @INFO sortI= 1(작성 순), sortId = 2(가나다 순)
     */
    @ApiOperation(value = "홈 화면 전체 조회")
    @Auth
    @GetMapping("")
    public ResponseEntity<ResponseDto> home(@RequestParam("state") int bucketState,
                                            @RequestParam("category") int category,
                                            @RequestParam("sort") int sort) {
        Long userId = UserContext.getCurrentUserId();

        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.GET_BUCKET_LIST,
                        bucketService.homeBucketList(bucketState, category, userId, sort)));
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
