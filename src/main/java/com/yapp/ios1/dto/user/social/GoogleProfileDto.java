package com.yapp.ios1.dto.user.social;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * created by ayoung 2021/05/04
 */
@NoArgsConstructor
@Getter
public class GoogleProfileDto {
    private String sub;

    private String name;

    private String given_name;

    private String family_name;

    private String picture;

    private String email;

    private Boolean email_verified;

    private String locale;
}
