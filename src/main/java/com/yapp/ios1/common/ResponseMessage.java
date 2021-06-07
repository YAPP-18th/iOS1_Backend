package com.yapp.ios1.common;

/**
 * created by jg 2021/05/05
 */
public class ResponseMessage {
    private ResponseMessage() {}

    // Bucket
    public static final String GET_BUCKET_LIST = "버킷 리스트 조회 성공입니다.";
    public static final String UPLOAD_IMAGE_SUCCESS = "이미지 업로드 성공입니다.";
    public static final String NOT_EXIST_IMAGE = "업로드할 이미지 존재하지 않습니다.";
    public static final String REGISTER_BUCKET_SUCCESS = "버킷 등록 성공입니다.";
    public static final String UPDATE_BUCKET_SUCCESS = "버킷 수정 성공입니다.";
    public static final String GET_BUCKET_DETAIL = "버킷 상세 조회 성공입니다";
    public static final String REGISTER_BUCKET_FIN_SUCCESS = "버킷 핀 등록 성공입니다";

    // Login
    public static final String LOGIN_SUCCESS = "AccessToken, RefreshToken 발급합니다.";
    public static final String SIGN_UP_SUCCESS = "회원가입 성공입니다.";
    public static final String CHANGE_PASSWORD_SUCCESS = "비밀번호 재설정 성공입니다.";
    public static final String POSSIBLE_NICKNAME = "사용가능한 닉네임 입니다.";
    public static final String POSSIBLE_EMAIL = "사용가능한 이메일 입니다.";

    // User
    public static final String GET_PROFILE_SUCCESS = "프로필 가져오기 성공입니다.";
    public static final String UPDATE_PROFILE_SUCCESS = "프로필 수정 성공입니다.";
    public static final String GET_MY_INFO = "마이페이지 정보입니다.";
    public static final String GET_USER_INFO = "사용자페이지 정보입니다.";
    public static final String GET_FRIEND_LIST = "친구 목록입니다.";
    public static final String DELETE_USER_SUCCESS = "탈퇴 성공입니다.";

    // Notification
    public static final String GET_ALARM_LOG = "알람 로그 조회 성공입니다";
    public static final String DELETE_ALARM_LOG = "알람 로그 삭제되었습니다";

    // Search
    public static final String NOT_FOUND_SEARCH_TYPE = "존재하지 않는 검색 조건입니다.";
    public static final String SUCCESS_SEARCH = "검색 성공입니다.";

    // Follow
    public static final String FRIEND_REQUEST = "팔로우 요청 성공입니다.";
    public static final String FRIEND_MESSAGE = "팔로우 응답 성공입니다.";

    // Email
    public static final String EMAIL_SEND_SUCCESS = "이메일 전송 성공입니다.";
    public static final String EMAIL_AUTH_SUCCESS = "이메일 인증 성공입니다.";
}
