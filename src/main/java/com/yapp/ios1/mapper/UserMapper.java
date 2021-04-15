package com.yapp.ios1.mapper;

import com.yapp.ios1.dto.user.SignUpDto;
import com.yapp.ios1.dto.user.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

/**
 * created by jg 2021/03/28
 */
@Mapper
public interface UserMapper {

    // 나중에 삭제 예정 (테스트 용)
    void test(@Param("userDto") UserDto userDto);

    Optional<UserDto> findByUserId(@Param("userId") long userId);

    Optional<UserDto> findByEmail(@Param("email") String email);

    int signUp(UserDto userDto);
}
