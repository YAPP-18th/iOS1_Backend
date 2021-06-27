package com.yapp.ios1.mapper;

import com.yapp.ios1.model.user.Friend;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * created by ayoung 2021/05/11
 */
@Mapper
public interface FriendMapper {

    int getFollowCountByUserId(Long userId);

    List<Friend> getFollowListByUserId(Long userId);

    int checkFriendStatus(Long myUserId, Long otherUserId);

    void requestFollow(@Param("myUserId") Long myUserId,
                       @Param("friendId") Long friendId,
                       @Param("followRequest") int followRequest,
                       @Param("alarmId") Long alarmId);

    void acceptFollow(@Param("myUserId") Long myUserId,
                      @Param("friendId") Long friendId,
                      @Param("friendStatus") int friendStatus);
}
