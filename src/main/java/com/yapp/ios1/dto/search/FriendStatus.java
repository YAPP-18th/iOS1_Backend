package com.yapp.ios1.dto.search;

/**
 * created by jg 2021/05/20
 */
public enum FriendStatus {
    FRIEND("친구"),
    REQUEST("요청"),
    NO_FRIENDS("친구아님");

    private final String friendStatus;

    FriendStatus(String friendStatus) {
        this.friendStatus = friendStatus;
    }

    public String getFriendStatus() {
        return friendStatus;
    }
}
