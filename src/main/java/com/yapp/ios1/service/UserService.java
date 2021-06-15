package com.yapp.ios1.service;

import com.yapp.ios1.dto.jwt.TokenResponseDto;
import com.yapp.ios1.error.exception.user.DeviceTokenNotFoundException;
import com.yapp.ios1.error.exception.user.UserNotFoundException;
import com.yapp.ios1.mapper.UserMapper;
import com.yapp.ios1.model.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * created by jg 2021/03/28
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    // 최신 알림 로그 확인한 상태(true), 확인하지 않은 알림 로그가 존재하는 상태(false)
    public void updateUserAlarmReadStatus(Long userId, boolean alarmReadStatus) {
        userMapper.updateAlarmStatus(userId, alarmReadStatus);
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

    public void deleteUser(Long userId) {
        userMapper.deleteUser(userId);
    }
}