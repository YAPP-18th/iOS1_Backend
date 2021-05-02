package com.yapp.ios1.utils.auth;

import com.yapp.ios1.dto.user.UserDto;

/**
 * created by ayoung 2021/05/01
 */
public class UserContext {
    public static ThreadLocal<UserDto> currentUser = new ThreadLocal<>();
}