package com.yapp.ios1.dto.bucket;

import lombok.Getter;

import java.util.List;

/**
 * created by ayoung 2021/05/11
 */
@Getter
public class BookmarkResultDto {
    private List<BookmarkDto> bookmarkList;
    private int bookMarkCount;

    public BookmarkResultDto(List<BookmarkDto> bookmarkList, int bookMarkCount) {
        this.bookmarkList = bookmarkList;
        this.bookMarkCount = bookMarkCount;
    }
}
