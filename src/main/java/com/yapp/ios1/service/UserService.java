package com.yapp.ios1.service;

import com.yapp.ios1.dto.bucket.BookmarkDto;
import com.yapp.ios1.dto.bucket.BookmarkResultDto;
import com.yapp.ios1.dto.user.ProfileDto;
import com.yapp.ios1.dto.user.ProfileResultDto;
import com.yapp.ios1.dto.user.UserDto;
import com.yapp.ios1.dto.user.login.SignInDto;
import com.yapp.ios1.dto.user.result.UserInfoDto;
import com.yapp.ios1.error.exception.common.BadRequestException;
import com.yapp.ios1.error.exception.user.NickNameDuplicatedException;
import com.yapp.ios1.error.exception.user.PasswordNotMatchException;
import com.yapp.ios1.error.exception.user.UserNotFoundException;
import com.yapp.ios1.mapper.FollowMapper;
import com.yapp.ios1.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.yapp.ios1.common.ResponseMessage.*;

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

    /**
     * 이메일 존재하는지 확인
     *
     * @param email 이메일
     */
    public Optional<UserDto> emailCheck(String email) {
        return userMapper.findByEmail(email);
    }

    /**
     * 닉네임 존재하는지 확인
     *
     * @param nickname 닉네임
     */
    public Optional<UserDto> nicknameCheck(String nickname) {
        return userMapper.findByNickname(nickname);
    }

    /**
     * 소셜 ID 존재하는지 확인
     *
     * @param socialId 소셜 아이디
     */
    public Optional<UserDto> socialIdCheck(String socialId) {
        return userMapper.findBySocialId(socialId);
    }

    /**
     * 이메일 or 닉네임 중복 확인
     *
     * @param email    이메일
     * @param nickname 닉네임
     */
    public Optional<UserDto> signUpCheck(String email, String nickname) throws SQLException {
        try {
            return userMapper.findByEmailOrNickname(email, nickname);
        } catch (Exception e) {
            throw new SQLException(DATABASE_ERROR);
        }
    }

    /**
     * 회원가입
     *
     * @param userDto 회원가입 정보
     */
    public Long signUp(UserDto userDto) {
        userDto.encodePassword(passwordEncoder.encode(userDto.getPassword()));
        userMapper.signUp(userDto);
        return userDto.getId();
    }

    /**
     * 이메일, 비밀번호 확인
     *
     * @param signInDto 로그인 정보
     * @return UserDto 비밀번호 확인까지 완료한 UserDto
     */
    public UserDto getUser(SignInDto signInDto) {
        Optional<UserDto> optional = emailCheck(signInDto.getEmail());
        if (optional.isEmpty()) {
            throw new UserNotFoundException();
        }
        UserDto user = optional.get();
        if (passwordEncoder.matches(signInDto.getPassword(), user.getPassword())) {
            return user;
        }
        throw new PasswordNotMatchException();
    }

    @Transactional
    public void changePassword(Long userId, String password) {
        String encodePassword = passwordEncoder.encode(password);
        userMapper.changePassword(userId, encodePassword);
    }

    // 프로필 정보 GET
    public ProfileResultDto getProfile(Long userId) {
        Optional<ProfileResultDto> optional = userMapper.findProfileByUserId(userId);
        if (optional.isEmpty()) {
            throw new UserNotFoundException();
        }
        return optional.get();
    }

    // 프로필 업데이트
    @Transactional
    public void updateProfile(ProfileDto profileDto, Long userId) {
        int change = userMapper.updateProfile(profileDto, userId);
        if (change == 0) { // 닉네임 중복인 경우
            throw new NickNameDuplicatedException();
        }
    }

    @Transactional(readOnly = true)
    public UserInfoDto getOtherUserInfo(Long currentUserId, Long userId) {
        UserInfoDto userInfo = getUserInfo(userId);

        Optional<Long> checkFriend = followMapper.isFriendByCurrentUserIdAndUserId(currentUserId, userId);

        if (checkFriend.isEmpty()) { // 친구 아닌 경우
            userInfo.setFriend(Boolean.FALSE);
            return userInfo;
        }

        userInfo.setBucket(bucketService.getUserBucketList(userId));
        userInfo.setFriend(Boolean.TRUE);

        return userInfo;
    }

    // 마이페이지 get
    @Transactional(readOnly = true)
    public UserInfoDto getUserInfo(Long userId) {
        // 프로필 정보
        Optional<ProfileResultDto> optionalUser = userMapper.findProfileByUserId(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        // 친구 수, 버킷 수
        int friendCount = followMapper.getFollowCountByUserId(userId);
        int bucketCount = bucketService.getBucketCountByUserId(userId);

        // 북마크 수, 북마크
        List<BookmarkDto> bookmarkList = bucketService.getBookmarkList(userId);

        return UserInfoDto.builder()
                .user(optionalUser.get())
                .friendCount(friendCount)
                .bucketCount(bucketCount)
                .bookmark(new BookmarkResultDto(bookmarkList, bookmarkList.size()))
                .build();
    }
}