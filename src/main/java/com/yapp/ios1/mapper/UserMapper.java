package com.yapp.ios1.mapper;

import com.yapp.ios1.dto.user.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

/**
 * created by jg 2021/03/28
 */
@Mapper
public interface UserMapper {

    Optional<UserDto> findByUserId(@Param("userId") long userId);

    Optional<UserDto> findByEmail(@Param("email") String email);

    Optional<UserDto> findByNickname(@Param("nickname") String nickname);

    Optional<UserDto> findByEmailOrNickname(@Param("email") String email, @Param("nickname") String nickname);

    int signUp(UserDto userDto);
}
