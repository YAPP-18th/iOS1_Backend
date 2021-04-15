package com.yapp.ios1.dto.user;

import lombok.Getter;
import lombok.Setter;

/**
 * created by ayoung 2021/04/14
 */
@Getter
@Setter
public class SignUpDto {
    private String email;
    private String password;
    private String nickname;
    private String intro;
}
