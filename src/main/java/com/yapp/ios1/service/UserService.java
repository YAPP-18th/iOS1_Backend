package com.yapp.ios1.service;

import com.yapp.ios1.model.user.User;
import com.yapp.ios1.model.bookmark.Bookmark;
import com.yapp.ios1.dto.bucket.BookmarkListDto;
import com.yapp.ios1.dto.jwt.TokenResponseDto;
import com.yapp.ios1.controller.dto.user.ProfileUpdateDto;
import com.yapp.ios1.controller.dto.user.login.SignInDto;
import com.yapp.ios1.dto.user.UserInfoDto;
import com.yapp.ios1.error.exception.user.*;
import com.yapp.ios1.mapper.FollowMapper;
import com.yapp.ios1.mapper.UserMapper;
import com.yapp.ios1.model.user.Profile;
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
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final FollowMapper followMapper;
    private final JwtService jwtService;

    public String findDeviceTokenByUserId(Long userId) {
        return userMapper.findDeviceTokenByUserId(userId)
                .orElseThrow(DeviceTokenNotFoundException::new);
    }

    public User findByUserId(Long userId) {
        return userMapper.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public void emailCheck(String email) {
        Optional<User> user = userMapper.findByEmail(email);
        if (user.isPresent()) {
            throw new EmailDuplicatedException();
        }
    }

    public void checkEmailPresent(String email) {
        userMapper.findByEmail(email)
                .orElseThrow(EmailNotExistException::new);
    }

    public void nicknameCheck(String nickname) {
        Optional<User> user = userMapper.findByNickname(nickname);
        if (user.isPresent()) {
            throw new NickNameDuplicatedException();
        }
    }

    public Optional<User> findBySocialIdAndSocialType(String socialId, String socialType) {
        return userMapper.findBySocialIdAndSocialType(socialId, socialType);
    }

    @Transactional
    public TokenResponseDto signUp(User user) {
        user.encodePassword(passwordEncoder.encode(user.getPassword()));
        userMapper.signUp(user);
        return jwtService.createTokenResponse(user.getId());
    }

    public User checkPassword(SignInDto signInDto) {
        User user = userMapper.findByEmail(signInDto.getEmail())
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(signInDto.getPassword(), user.getPassword())) {
            throw new PasswordNotMatchException();
        }
        return user;
    }

    @Transactional
    public void changePassword(Long userId, String password) {
        String encodePassword = passwordEncoder.encode(password);
        userMapper.changePassword(userId, encodePassword);
    }

    public Profile getProfile(Long userId) {
        return userMapper.findProfileByUserId(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public void updateProfile(ProfileUpdateDto profileUpdateDto, Long userId) {
        int change = userMapper.updateProfile(profileUpdateDto, userId);
        if (change == 0) {
            throw new NickNameDuplicatedException();
        }
    }

    @Transactional(readOnly = true)
    public UserInfoDto getOtherUserInfo(Long myUserId, Long otherUserId) {
        UserInfoDto userInfo = getUserInfo(otherUserId);

        int checkFriend = followMapper.checkFriendByMyUserIdAndOtherUserId(myUserId, otherUserId);

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
        Profile profile = userMapper.findProfileByUserId(userId)
                .orElseThrow(UserNotFoundException::new);

        int friendCount = followMapper.getFollowCountByUserId(userId);
        int bucketCount = bucketService.getBucketCountByUserId(userId);

        List<Bookmark> bookmarkList = bucketService.getBookmarkList(userId);

        return UserInfoDto.builder()
                .user(profile)
                .friendCount(friendCount)
                .bucketCount(bucketCount)
                .bookmark(new BookmarkListDto(bookmarkList, bookmarkList.size()))
                .build();
    }

    @Transactional
    public void deleteUser(Long userId) {
        userMapper.deleteUser(userId);
    }
}