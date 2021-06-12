package com.yapp.ios1.controller;

import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.controller.dto.bucket.BucketRequestDto;
import com.yapp.ios1.service.BucketService;
import com.yapp.ios1.annotation.Auth;
import com.yapp.ios1.aop.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.yapp.ios1.message.ResponseMessage.*;

/**
 * created by jg 2021/05/05
 */
@Api(tags = "Bucket")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/buckets")
public class BucketController {

    private final BucketService bucketService;

    /**
     * @INFO state 1(전체), 2(예정), 3(진행 중), 4(완료), 5(실패)
     * @INFO category 1(전체), 2(여행), 3(취미), 4(소유), 5(재정), 6(건강)
                      7(목표), 8(조직), 9(봉사), 10(기타)
     * @INFO sortI= 1(작성 순), sortId = 2(가나다 순)
     */
    @ApiOperation(value = "홈 화면 버킷 조회")
    @Auth
    @GetMapping("")
    public ResponseEntity<ResponseDto> homeBucket(@RequestParam("state") int bucketState,
                                                  @RequestParam("category") int category,
                                                  @RequestParam("sort") int sort) {
        Long userId = UserContext.getCurrentUserId();
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, GET_BUCKET_LIST,
                bucketService.getHomeBucketList(bucketState, category, userId, sort)));
    }

    @ApiOperation(value = "버킷 상세 조회")
    @Auth
    @GetMapping("/{bucketId}")
    public ResponseEntity<ResponseDto> bucketOne(@PathVariable Long bucketId) {
        Long userId = UserContext.getCurrentUserId();
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, GET_BUCKET_DETAIL, bucketService.getBucketDetail(bucketId, userId)));
    }

    // bucketStateId 2(예정), 3(진행 중), 4(완료), 5(실패)
    @ApiOperation(value = "버킷 상태 변경")
    @Auth
    @PutMapping("/{bucketId}/state/{bucketStateId}")
    public ResponseEntity<ResponseDto> updateBucketState(@PathVariable Long bucketId,
                                                         @PathVariable int bucketStateId) {
        Long userId = UserContext.getCurrentUserId();
        bucketService.updateBucketState(userId, bucketId, bucketStateId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, UPDATE_BUCKET_STATE));
    }

    @ApiOperation(value = "버킷 핀 설정 or 해제")
    @Auth
    @PutMapping("/{bucketId}/fin")
    public ResponseEntity<ResponseDto> registerBucketFin(@PathVariable Long bucketId, @RequestParam("state") boolean isFin) {
        Long userId = UserContext.getCurrentUserId();
        bucketService.setBucketFin(bucketId, userId, isFin);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, REGISTER_BUCKET_FIN_SUCCESS));
    }

    @ApiOperation(value = "버킷 등록")
    @Auth
    @PostMapping("")
    public ResponseEntity<ResponseDto> registerBucket(@RequestBody @Valid BucketRequestDto bucket) {
        bucket.setUserId(UserContext.getCurrentUserId());
        bucketService.saveBucket(bucket);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.CREATED, REGISTER_BUCKET_SUCCESS));
    }

    @ApiOperation(value = "버킷 수정")
    @Auth
    @PutMapping("/{bucketId}")
    public ResponseEntity<ResponseDto> updateBucket(@PathVariable Long bucketId, @RequestBody @Valid BucketRequestDto requestDto) {
        bucketService.updateBucket(bucketId, requestDto, UserContext.getCurrentUserId());
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, UPDATE_BUCKET_SUCCESS));
    }

    @ApiOperation(value = "북마크 설정 or 해제")
    @Auth
    @PutMapping("/{bucketId}/bookmark")
    public ResponseEntity<ResponseDto> setBookmark(@PathVariable("bucketId") Long bucketId, @RequestParam("state") boolean isBookmark) {
        Long userId = UserContext.getCurrentUserId();
        bucketService.saveBookmark(bucketId, userId, isBookmark);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK, UPDATE_BUCKET_SUCCESS));
    }
}
