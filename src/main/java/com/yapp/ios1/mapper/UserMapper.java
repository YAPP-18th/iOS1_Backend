package com.yapp.ios1.mapper;

import com.yapp.ios1.dto.user.ProfileDto;
import com.yapp.ios1.dto.user.ProfileResultDto;
import com.yapp.ios1.dto.user.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * created by jg 2021/03/28
 */
@Mapper
public interface UserMapper {

    Optional<UserDto> findByUserId(@Param("userId") Long userId);

    Optional<UserDto> findByEmail(@Param("email") String email);

    Optional<UserDto> findByNickname(@Param("nickname") String nickname);

    Optional<UserDto> findBySocialId(@Param("socialId") String socialId);

    Optional<UserDto> findByEmailOrNickname(@Param("email") String email, @Param("nickname") String nickname);

    Optional<ProfileResultDto> findProfileByUserId(@Param("userId") Long userId);

    void changePassword(Long userId, String password);

    int updateProfile(ProfileDto profile, Long userId);

    void signUp(UserDto userDto);

    String findDeviceTokenByUserId(Long userId);

    List<String> findAllUserDeviceToken();

    Long findUserIdByEmail(String email);
}
