package com.yapp.ios1.utils.follow;

/**
 * created by jg 2021/05/22
 */
// TODO 리팩터링 (삭제 or 수정)
public enum FollowNotification {
    FOLLOW_REQUEST_TITLE("팔로우 요청 제목"),
    FOLLOW_REQUEST_MESSAGE("팔로우 요청 메세지"),
    FOLLOW_ACCEPT_TITLE("팔로우 승낙 제목"),
    FOLLOW_ACCEPT_MESSAGE("팔로우 승낙 메세지");

    private final String message;

    FollowNotification(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
