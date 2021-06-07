package com.yapp.ios1.controller.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * created by ayoung 2021/05/18
 */
@AllArgsConstructor
@Getter
public class ProfileUpdateDto {
    @JsonIgnore
    private Long userId;
    @NotBlank
    private String nickname;
    @NotNull
    private String intro;
    @NotNull
    private String profileUrl;

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
