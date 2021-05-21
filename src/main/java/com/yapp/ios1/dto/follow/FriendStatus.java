package com.yapp.ios1.dto.follow;

/**
 * created by jg 2021/05/21
 */
public enum FriendStatus {
    FRIEND(1),      // 친구
    REQUEST(2),     // 요청 중
    NOT_FRIEND(3);  // 친구 아님

    private final int friendStatus;

    FriendStatus(int friendStatus) {
        this.friendStatus = friendStatus;
    }

    public int getFriendStatus() {
        return friendStatus;
    }
}
