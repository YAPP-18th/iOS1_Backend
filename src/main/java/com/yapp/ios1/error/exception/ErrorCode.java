package com.yapp.ios1.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * created by jg 2021/06/03
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT) // enum 에서 key-value 쌍을 사용할 때
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
    ENTITY_NOT_FOUND(400, "C003", " Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),
    JSON_WRITE_ERROR(401, "C007", "JSON content that are not pure I/O problems"),

    // JWT
    JWT_ERROR(401, "J001", "JWT가 없거나 잘못된 값 입니다"),
    JWT_PARSE_ERROR(401, "J002", "JWT Parse Error"),
    JWT_EXPIRED_ERROR(401, "J003", "JWT Expired Error"),

    // User
    EMAIL_NOT_FOUND(400, "M000", "Email is not Exist"),
    EMAIL_DUPLICATION(400, "M001", "Email is Duplication"),
    LOGIN_INPUT_INVALID(400, "M002", "Login input is invalid"),
    USER_NOT_FOUND(400, "M003", "User is not Exist"),
    NICKNAME_EMAIL_DUPLICATION(400, "M004", "NickName is Duplication"),
    USER_NOT_AUTHENTICATION(401, "M005", "User is not Authentication"),
    SOCIAL_LOGIN_TOKEN_ERROR(401, "M006", "Social Login is Token Error"),
    SOCIAL_TYPE_NOT_FOUND(400, "M007", "Social Type is not Exist"),
    PASSWORD_MISMATCH_ERROR(400, "M008", "Password MisMatch Error"),
    DEVICE_TOKEN_NOT_FOUND_ERROR(400, "M008", "DeviceToken is not Exist"),

    // Email
    EMAIL_SEND_ERROR(500, "P001", "Email Send Error"),

    // AWS
    AWS_S3_ERROR(500, "A001", "AWS S3 Error");

    // Valid
    public static final String NOT_VALID_BUCKET_NAME = "버킷 제목 작성 필수입니다.";
    public static final String NOT_VALID_CATEGORY_ID = "유효한 카테고리 ID가 아닙니다.";
    public static final String NOT_VALID_STATE = "유효한 버킷 state가 아닙니다.";
    public static final String NOT_VALID_EMAIL = "이메일 형식 제대로 입력해주세요.";

    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }
}
