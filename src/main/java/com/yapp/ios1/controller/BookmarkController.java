package com.yapp.ios1.controller;

import com.yapp.ios1.common.ResponseMessage;
import com.yapp.ios1.dto.ResponseDto;
import com.yapp.ios1.service.BucketService;
import com.yapp.ios1.utils.auth.Auth;
import com.yapp.ios1.utils.auth.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * created by ayoung 2021/06/01
 */
@Api(tags = "Bookmark")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
public class BookmarkController {

    private final BucketService bucketService;

    @ApiOperation(value = "북마크 설정",
    notes = "북마크 설정 / 해제 합니다.")
    @Auth
    @PutMapping("/buckets/{id}/bookmark")
    public ResponseEntity<ResponseDto> setBookmark(@PathVariable("id") Long bucketId, @RequestParam("state") boolean isBookmark) {
        Long userId = UserContext.getCurrentUserId();
        bucketService.setBookmark(bucketId, userId, isBookmark);
        return ResponseEntity.ok()
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.UPDATE_BUCKET_SUCCESS));
    }
}