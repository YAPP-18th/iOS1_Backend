package com.yapp.ios1.mapper;

import com.yapp.ios1.controller.dto.user.ProfileUpdateDto;
import com.yapp.ios1.model.user.User;
import com.yapp.ios1.model.user.Profile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * created by jg 2021/03/28
 */
@Mapper
public interface UserMapper {

    Optional<User> findByUserId(@Param("userId") Long userId);

    Optional<User> findByEmail(@Param("email") String email);

    Optional<User> findByNickname(@Param("nickname") String nickname);

    Optional<User> findBySocialIdAndSocialType(String socialId, String socialType);

    void changePassword(Long userId, String password);

    void signUp(User user);

    Optional<String> findDeviceTokenByUserId(Long userId);

    List<String> findAllUserDeviceToken();

    void deleteUser(Long userId);
}
