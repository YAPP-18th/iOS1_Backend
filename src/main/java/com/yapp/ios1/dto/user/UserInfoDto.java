package com.yapp.ios1.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yapp.ios1.dto.bucket.BookmarkResultDto;
import com.yapp.ios1.dto.bucket.BucketDto;
import com.yapp.ios1.dto.bucket.BucketResultDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isFriend;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<BucketDto> bucket;

    public void setBucket(List<BucketDto> bucket) {
        this.bucket = bucket;
    }

    public void setFriend(Boolean friend) {
        this.isFriend = friend;
    }

}
