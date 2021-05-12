package com.yapp.ios1.mapper;

import com.yapp.ios1.dto.user.FriendDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * created by ayoung 2021/05/11
 */
@Mapper
public interface FollowMapper {

    int getFollowCountByUserId(Long userId);

    List<FriendDto> getFollowListByUserId(Long userId);
}
