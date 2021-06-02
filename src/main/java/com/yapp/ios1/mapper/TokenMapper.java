package com.yapp.ios1.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * created by ayoung 2021/06/03
 */
@Mapper
public interface TokenMapper {
    void updateToken(String refreshToken, Long userId);
}
