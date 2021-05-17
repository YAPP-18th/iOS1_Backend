package com.yapp.ios1.dto.user.social;

import lombok.Getter;

/**
 * created by ayoung 2021/05/15
 */
@Getter
public class AppleRequestDto {
    private String userIdentity;
    private String name;
    private String email;
    private String identityToken;
}
