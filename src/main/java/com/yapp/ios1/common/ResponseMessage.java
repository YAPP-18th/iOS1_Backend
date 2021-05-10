package com.yapp.ios1.common;

/**
 * created by jg 2021/05/05
 */
public class ResponseMessage {
    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러입니다.";
    public static final String NOT_FOUND_USER = "존재하지 않는 유저입니다.";

    // Bucket
    public static final String GET_BUCKET_LIST = "버킷 리스트 조회 성공입니다.";
    public static final String REGISTER_BUCKET_SUCCESS = "버킷 등록 성공입니다.";
    public static final String NOT_FOUND_CATEGORY = "존재하지 않는 카테고리 입니다.";

    // Login
    public static final String LOGIN_SUCCESS = "액세스 토큰 발급";
    public static final String SOCIAL_LOGIN_ERROR = "소셜 로그인 실패입니다.";
    public static final String EXIST_USER = "존재하는 회원입니다.";
    public static final String NOT_EXIST_USER = "존재하지 않는 회원입니다.";
    public static final String SIGN_UP_SUCCESS = "회원가입 성공입니다.";
    public static final String NOT_MATCH_PASSWORD = "비밀번호 오류입니다.";
    public static final String BAD_SOCIAL_TYPE = "잘못된 소셜 타입입니다.";

    public static final String DATABASE_ERROR = "데이터베이스 오류입니다.";
}
