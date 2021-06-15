package com.yapp.ios1.service;

import com.yapp.ios1.error.exception.user.DeviceTokenNotFoundException;
import com.yapp.ios1.error.exception.user.UserNotFoundException;
import com.yapp.ios1.mapper.UserMapper;
import com.yapp.ios1.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * created by jg 2021/06/15
 */
@RequiredArgsConstructor
@Service
public class UserFindService {

    private final UserMapper userMapper;

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
}
