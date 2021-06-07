package com.yapp.ios1.controller.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * created by ayoung 2021/05/18
 */
@AllArgsConstructor
@Getter
public class ProfileUpdateDto {
    @JsonIgnore
    private Long userId;
    private String nickname;
    private String intro;
    private String profileUrl;

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
