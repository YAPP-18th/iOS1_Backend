package com.yapp.ios1.mapper;

import com.yapp.ios1.controller.dto.user.ProfileUpdateDto;
import com.yapp.ios1.model.user.Profile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

/**
 * created by jg 2021/06/11
 */
@Mapper
public interface ProfileMapper {

    Optional<Profile> findProfileByUserId(@Param("userId") Long userId);
    int updateProfile(ProfileUpdateDto profile, Long userId);
}
