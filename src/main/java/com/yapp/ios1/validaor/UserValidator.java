package com.yapp.ios1.validaor;

import com.yapp.ios1.controller.dto.user.login.SignInDto;
import com.yapp.ios1.error.exception.user.*;
import com.yapp.ios1.mapper.UserMapper;
import com.yapp.ios1.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * created by jg 2021/06/10
 */
@RequiredArgsConstructor
@Component
public class UserValidator {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public void checkEmailDuplicate(String email) {
        Optional<User> user = userMapper.findByEmail(email);
        if (user.isPresent()) {
            throw new EmailDuplicatedException();
        }
    }

    public User checkEmailPresent(String email) {
        return userMapper.findByEmail(email)
                .orElseThrow(EmailNotExistException::new);
    }

    public void nicknameCheck(String nickname) {
        Optional<User> user = userMapper.findByNickname(nickname);
        if (user.isPresent()) {
            throw new NickNameDuplicatedException();
        }
    }

    public User checkPassword(SignInDto signInDto) {
        User user = userMapper.findByEmail(signInDto.getEmail())
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(signInDto.getPassword(), user.getPassword())) {
            throw new PasswordNotMatchException();
        }

        return user;
    }
}
