package com.yapp.ios1.dto.bucket;

import com.yapp.ios1.model.bookmark.Bookmark;
import lombok.Getter;

import java.util.List;

/**
 * created by ayoung 2021/05/11
 * 북마크 Dto
 */
@Getter
public class BookmarkListDto {
    private List<Bookmark> bookmarkList;
    private int bookMarkCount;

    public BookmarkListDto(List<Bookmark> bookmarkList, int bookMarkCount) {
        this.bookmarkList = bookmarkList;
        this.bookMarkCount = bookMarkCount;
    }
}
