package com.yapp.ios1.utils.auth;

import com.yapp.ios1.dto.user.UserDto;

public class UserContext {
    public static ThreadLocal<UserDto> currentUser = new ThreadLocal<>();
}