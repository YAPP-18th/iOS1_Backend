package com.yapp.ios1.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * created by ayoung 2021/05/11
 */
@Mapper
public interface FollowMapper {

    int getFollowCountByUserId(Long userId);

}
