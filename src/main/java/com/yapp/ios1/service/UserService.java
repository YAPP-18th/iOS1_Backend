package com.yapp.ios1.service;

import com.yapp.ios1.dto.user.SignInDto;
import com.yapp.ios1.dto.user.SignUpDto;
import com.yapp.ios1.dto.user.UserDto;
import com.yapp.ios1.exception.PasswordNotMatchException;
import com.yapp.ios1.exception.UserNotFoundException;
import com.yapp.ios1.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
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
        if (res != 1) {
            throw new SQLException("데이터베이스에 저장되지 않았습니다.");
        }
    }

    /**
     * 이메일, 비밀번호 확인
     *
     * @param signInDto 로그인 정보
     * @return UserDto 비밀번호 확인까지 완료한 UserDto
     */
    public UserDto getMember(SignInDto signInDto) {
        Optional<UserDto> optional = emailCheck(signInDto.getEmail());
        if (optional.isEmpty()) {
            throw new UserNotFoundException("존재하지 않는 유저입니다.");
        }
        UserDto user = optional.get();
        if (passwordEncoder.matches(signInDto.getPassword(), user.getPassword())) {
            return user;
        }
        throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
    }
}
