package com.yapp.ios1.dto.follow;

/**
 * created by jg 2021/05/21
 */
public enum FriendStatus {
    FRIEND(1),
    REQUEST(2),
    NOT_FRIEND(3);

    private final int friendStatus;

    FriendStatus(int friendStatus) {
        this.friendStatus = friendStatus;
    }

    public int getFriendStatus() {
        return friendStatus;
    }
}
