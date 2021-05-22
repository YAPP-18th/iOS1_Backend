package com.yapp.ios1.dto.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * created by jg 2021/04/11
 */
@Getter
@NoArgsConstructor
public class JwtPayload {
    private long id;

    public JwtPayload(long id) {
        this.id = id;
    }
}
