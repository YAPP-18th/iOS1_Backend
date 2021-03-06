package com.yapp.ios1.service.user;

import com.yapp.ios1.dto.bookmark.BookmarkListDto;
import com.yapp.ios1.dto.user.UserInfoDto;
import com.yapp.ios1.mapper.FriendMapper;
import com.yapp.ios1.model.bookmark.Bookmark;
import com.yapp.ios1.model.user.Profile;
import com.yapp.ios1.service.bucket.BucketFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * created by ayoung 2021/06/12
 */
@RequiredArgsConstructor
@Service
public class UserInfoService {

    private final ProfileService profileService;
    private final BucketFindService bucketFindService;
    private final FriendMapper friendMapper;

    @Transactional(readOnly = true)
    public UserInfoDto getOtherUserInfo(Long myUserId, Long otherUserId) {
        UserInfoDto userInfo = getUserInfo(otherUserId);

        int friendStatus = friendMapper.checkFriendStatus(myUserId, otherUserId);
        userInfo.setFriend(friendStatus);
        // 친구 일 때
        if (friendStatus == 1) {
            userInfo.setBucket(bucketFindService.getUserBucketList(otherUserId));
        }

        return userInfo;
    }

    @Transactional(readOnly = true)
    public UserInfoDto getUserInfo(Long userId) {
        Profile profile = profileService.getProfile(userId);

        int friendCount = friendMapper.getFollowCountByUserId(userId);
        int bucketCount = bucketFindService.getBucketCountByUserId(userId);

        List<Bookmark> bookmarkList = bucketFindService.getBookmarkList(userId);

        return UserInfoDto.builder()
                .user(profile)
                .friendCount(friendCount)
                .bucketCount(bucketCount)
                .bookmark(new BookmarkListDto(bookmarkList, bookmarkList.size()))
                .build();
    }
}
