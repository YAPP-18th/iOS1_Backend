package com.yapp.ios1.dto.user.social;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * created by ayoung 2021/05/05
 */
@AllArgsConstructor
@Getter
public class UserCheckDto {
    private HttpStatus status;
    private Long userId;
}
