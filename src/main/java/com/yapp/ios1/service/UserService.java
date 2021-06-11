package com.yapp.ios1.service;

import com.yapp.ios1.dto.bookmark.BookmarkListDto;
import com.yapp.ios1.dto.jwt.TokenResponseDto;
import com.yapp.ios1.dto.user.UserInfoDto;
import com.yapp.ios1.error.exception.user.DeviceTokenNotFoundException;
import com.yapp.ios1.error.exception.user.UserNotFoundException;
import com.yapp.ios1.mapper.FriendMapper;
import com.yapp.ios1.mapper.ProfileMapper;
import com.yapp.ios1.mapper.UserMapper;
import com.yapp.ios1.model.bookmark.Bookmark;
import com.yapp.ios1.model.user.Profile;
import com.yapp.ios1.model.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * created by jg 2021/03/28
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final BucketService bucketService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final FriendMapper friendMapper;
    private final ProfileMapper profileMapper;

    public String getDeviceToken(Long userId) {
        return userMapper.findDeviceTokenByUserId(userId)
                .orElseThrow(DeviceTokenNotFoundException::new);
    }

    public List<String> getAllDeviceToken() {
        return userMapper.findAllUserDeviceToken();
    }

    public User getUser(Long userId) {
        return userMapper.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public Optional<User> findBySocialIdAndSocialType(String socialId, String socialType) {
        return userMapper.findBySocialIdAndSocialType(socialId, socialType);
    }

    public TokenResponseDto signUp(User user) {
        user.encodePassword(passwordEncoder.encode(user.getPassword()));
        userMapper.signUp(user);
        return jwtService.createTokenResponse(user.getId());
    }

    public void changePassword(Long userId, String password) {
        String encodePassword = passwordEncoder.encode(password);
        userMapper.changePassword(userId, encodePassword);
    }

    @Transactional(readOnly = true)
    public UserInfoDto getOtherUserInfo(Long myUserId, Long otherUserId) {
        UserInfoDto userInfo = getUserInfo(otherUserId);

        int checkFriend = friendMapper.checkFriendByMyUserIdAndOtherUserId(myUserId, otherUserId);

        if (checkFriend == 0) {
            userInfo.setFriend(Boolean.FALSE);
            return userInfo;
        }

        userInfo.setBucket(bucketService.getUserBucketList(otherUserId));
        userInfo.setFriend(Boolean.TRUE);

        return userInfo;
    }

    @Transactional(readOnly = true)
    public UserInfoDto getUserInfo(Long userId) {
        // TODO 모든 find 관련 로직도 Validator 처럼 다른 쪽으로 빼서 해볼까..
        Profile profile = profileMapper.findProfileByUserId(userId)
                .orElseThrow(UserNotFoundException::new);

        int friendCount = friendMapper.getFollowCountByUserId(userId);
        int bucketCount = bucketService.getBucketCountByUserId(userId);

        List<Bookmark> bookmarkList = bucketService.getBookmarkList(userId);

        return UserInfoDto.builder()
                .user(profile)
                .friendCount(friendCount)
                .bucketCount(bucketCount)
                .bookmark(new BookmarkListDto(bookmarkList, bookmarkList.size()))
                .build();
    }

    public void deleteUser(Long userId) {
        userMapper.deleteUser(userId);
    }
}