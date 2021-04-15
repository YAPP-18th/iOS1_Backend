package com.yapp.ios1.service;

import com.yapp.ios1.dto.user.SignUpDto;
import com.yapp.ios1.dto.user.UserDto;
import com.yapp.ios1.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

/**
 * created by jg 2021/03/28
 */
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * MyBatis 세팅을 위한 임시 코드 (삭제 예정)
     */
    public void test(final UserDto userDto) {
        userMapper.test(userDto);
    }

    /**
     * 이메일 존재하는지 확인
     *
     * @param email 이메일
     */
    public Optional<UserDto> emailCheck(String email) {
        return userMapper.findByEmail(email);
    }


    /**
     * 회원가입
     *
     * @param signUpDto 회원가입 정보
     */
    public void signUp(SignUpDto signUpDto) throws SQLException {
        signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        UserDto userDto = UserDto.of(signUpDto);
        int res = userMapper.signUp(userDto);
        if (res != 1) throw new SQLException("데이터베이스에 저장되지 않았습니다.");
    }
}
