package com.yapp.ios1.dto.user;

import com.yapp.ios1.dto.bucket.BookmarkResultDto;
import lombok.Builder;
import lombok.Getter;

/**
 * created by ayoung 2021/05/11
 */
@Getter
@Builder
public class UserInfoDto {
    private UserDto user;
    private int friendCount;
    private int bucketCount;
    private BookmarkResultDto bookmark;
}
