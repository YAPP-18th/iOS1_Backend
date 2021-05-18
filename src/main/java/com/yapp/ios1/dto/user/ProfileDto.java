package com.yapp.ios1.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * created by ayoung 2021/05/18
 */
@AllArgsConstructor
@Getter
public class ProfileDto {
    @JsonIgnore
    private Long userId;
    private String nickname;
    private String intro;
    @JsonIgnore
    private String profileUrl;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
