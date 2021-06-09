package com.yapp.ios1.controller.dto.email;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

/**
 * created by ayoung 2021/05/30
 */
@Getter
public class EmailCodeDto {
    @NotBlank
    private String code;
}
