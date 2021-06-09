package com.yapp.ios1.controller.dto.email;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static com.yapp.ios1.common.ValidMessage.NOT_VALID_EMAIL;

/**
 * created by ayoung 2021/04/20
 */
@Getter
public class EmailDto {
    @NotBlank
    @Email(message = NOT_VALID_EMAIL)
    private String email;
}

