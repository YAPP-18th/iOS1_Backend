package com.yapp.ios1.mapper;

import com.yapp.ios1.model.user.Friend;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * created by ayoung 2021/05/11
 */
@Mapper
public interface FollowMapper {

    int getFollowCountByUserId(Long userId);

    List<Friend> getFollowListByUserId(Long userId);

    int checkFriendByMyUserIdAndOtherUserId(Long myUserId, Long otherUserId);

    void followRequest(@Param("myUserId") Long myUserId,
                       @Param("friendId") Long friendId,
                       @Param("followRequest") int followRequest,
                       @Param("alarmId") Long alarmId);

    void followAccept(@Param("myUserId") Long myUserId,
                      @Param("friendId") Long friendId,
                      @Param("friendStatus") int friendStatus);
}
