package com.yapp.ios1.dto.bucket;

import lombok.Getter;

/**
 * created by ayoung 2021/06/01
 *
 * 북마크 설정, 해제 시 데이터베이스에서 조회하는 DTO
 */
@Getter
public class BookmarkUpdateDto {
    private Long id;
    private Boolean isBookmark;
    private Long userId;
}
