package com.yapp.ios1.common;

/**
 * created by jg 2021/05/05
 */
public class ResponseMessage {
    private ResponseMessage() {}

    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러입니다.";
    public static final String NOT_FOUND_USER = "존재하지 않는 유저입니다.";
    public static final String DATABASE_ERROR = "데이터베이스 오류입니다.";

    // Bucket
    public static final String GET_BUCKET_LIST = "버킷 리스트 조회 성공입니다.";
    public static final String UPLOAD_IMAGE_SUCCESS = "이미지 업로드 성공입니다.";
    public static final String NOT_EXIST_IMAGE = "업로드할 이미지 존재하지 않습니다.";
    public static final String REGISTER_BUCKET_SUCCESS = "버킷 등록 성공입니다.";
    public static final String NOT_FOUND_CATEGORY = "존재하지 않는 카테고리 입니다.";
    public static final String BAD_BUCKET_STATE = "잘못된 버킷 state 입니다.";
    public static final String NOT_FOUND_SORT = "잘못된 정렬 입니다.";
    public static final String NOT_FOUND_BUCKET = "존재하지 않는 버킷입니다.";

    // Login
    public static final String LOGIN_SUCCESS = "AccessToken, RefreshToken 발급합니다.";
    public static final String SOCIAL_LOGIN_ERROR = "소셜 로그인 실패입니다.";
    public static final String EXIST_EMAIL = "존재하는 이메일입니다.";
    public static final String EXIST_USER = "존재하는 회원입니다.";
    public static final String NOT_EXIST_USER = "존재하지 않는 회원입니다.";
    public static final String SIGN_UP_SUCCESS = "회원가입 성공입니다.";
    public static final String NOT_MATCH_PASSWORD = "비밀번호 오류입니다.";
    public static final String BAD_SOCIAL_TYPE = "잘못된 소셜 타입입니다.";
    public static final String BAD_JWT = "잘못된 JWT 입니다.";
    public static final String BAD_USER = "잘못된 접근입니다.";

    // User
    public static final String GET_PROFILE_SUCCESS = "프로필 가져오기 성공.";
    public static final String UPDATE_PROFILE_SUCCESS = "프로필 수정 성공.";
    public static final String GET_MY_INFO = "마이페이지 정보입니다.";
    public static final String GET_USER_INFO = "사용자페이지 정보입니다.";
    public static final String GET_FRIEND_LIST = "친구 목록입니다.";
    public static final String NO_FRIEND_LIST = "친구 목록이 없습니다.";

    // Notification
    public static final String FIREBASE_INIT_ERROR = "파이어베이스 초기화 오류입니다.";

    // Search
    public static final String NOT_FOUND_SEARCH_TYPE = "존재하지 않는 검색 조건입니다.";
    public static final String SUCCESS_SEARCH = "검색 성공입니다.";

    // Follow
    public static final String FRIEND_REQUEST = "팔로우 요청 성공입니다.";
    public static final String FRIEND_ACCEPT = "팔로우 승낙 성공입니다";
}
