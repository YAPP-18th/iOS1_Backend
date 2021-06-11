package com.yapp.ios1.service;

import com.yapp.ios1.controller.dto.user.ProfileUpdateDto;
import com.yapp.ios1.error.exception.user.NickNameDuplicatedException;
import com.yapp.ios1.error.exception.user.UserNotFoundException;
import com.yapp.ios1.mapper.ProfileMapper;
import com.yapp.ios1.model.user.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * created by jg 2021/06/11
 */
@RequiredArgsConstructor
@Service
public class ProfileService {

    private final ProfileMapper profileMapper;

    public Profile getProfile(Long userId) {
        return profileMapper.findProfileByUserId(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public void updateProfile(ProfileUpdateDto profileUpdateDto, Long userId) {
        int change = profileMapper.updateProfile(profileUpdateDto, userId);
        if (change == 0) {
            throw new NickNameDuplicatedException();
        }
    }
}
