package com.yapp.ios1.service;

import com.yapp.ios1.dto.user.UserDto;
import com.yapp.ios1.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * created by jg 2021/03/28
 */
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;

    /**
     * MyBatis 세팅을 위한 임시 코드 (삭제 예정)
     */
    public void test(final UserDto userDto) {
        userMapper.test(userDto);
    }
}
