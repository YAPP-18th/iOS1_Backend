package com.yapp.ios1.service.user;

import com.yapp.ios1.dto.bookmark.BookmarkListDto;
import com.yapp.ios1.dto.user.UserInfoDto;
import com.yapp.ios1.mapper.FriendMapper;
import com.yapp.ios1.model.bookmark.Bookmark;
import com.yapp.ios1.model.user.Profile;
import com.yapp.ios1.service.bucket.BucketFindService;
import lombok.RequiredArgsConstructor;
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

        int checkFriend = friendMapper.checkFriendByMyUserIdAndOtherUserId(myUserId, otherUserId);

        if (checkFriend == 0) {
            userInfo.setFriend(Boolean.FALSE);
            return userInfo;
        }

        userInfo.setBucket(bucketFindService.getUserBucketList(otherUserId));
        userInfo.setFriend(Boolean.TRUE);

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
