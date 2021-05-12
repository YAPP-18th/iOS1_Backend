package com.yapp.ios1.service;

import com.yapp.ios1.dto.bucket.BookmarkDto;
import com.yapp.ios1.dto.bucket.BookmarkResultDto;
import com.yapp.ios1.dto.user.SignInDto;
import com.yapp.ios1.dto.user.UserDto;
import com.yapp.ios1.dto.user.UserInfoDto;
import com.yapp.ios1.exception.user.PasswordNotMatchException;
import com.yapp.ios1.exception.user.UserNotFoundException;
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
    public Optional<UserDto> emailCheck(String email) throws SQLException {
        try {
            return userMapper.findByEmail(email);
        } catch (Exception e) {
            throw new SQLException(DATABASE_ERROR);
        }
    }

    /**
     * 닉네임 존재하는지 확인
     *
     * @param nickname 닉네임
     */
    public Optional<UserDto> nicknameCheck(String nickname) throws SQLException {
        try {
            return userMapper.findByNickname(nickname);
        } catch (Exception e) {
            throw new SQLException(DATABASE_ERROR);
        }
    }

    /**
     * 이메일 or 닉네임 중복 확인
     *
     * @param email 이메일
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
    public UserDto getUser(SignInDto signInDto) throws SQLException {
        try {
            Optional<UserDto> optional = emailCheck(signInDto.getEmail());
            if (optional.isEmpty()) {
                throw new UserNotFoundException(NOT_EXIST_USER);
            }
            UserDto user = optional.get();
            if (passwordEncoder.matches(signInDto.getPassword(), user.getPassword())) {
                return user;
            }
            throw new PasswordNotMatchException(NOT_MATCH_PASSWORD);
        } catch (Exception e) {
            throw new SQLException(DATABASE_ERROR);
        }
    }


    // 사용자 정보 get
    @Transactional(readOnly = true)
    public UserInfoDto getUserInfo(Long userId, boolean onlyPublic) {
        // 프로필 정보
        Optional<UserDto> optionalUser = userMapper.findByUserId(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(NOT_EXIST_USER);
        }

        // 친구 수, 버킷 수
        int friendCount = followMapper.getFollowCountByUserId(userId);
        int bucketCount = bucketService.getBucketCountByUserIdAndPublic(userId, onlyPublic);

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
