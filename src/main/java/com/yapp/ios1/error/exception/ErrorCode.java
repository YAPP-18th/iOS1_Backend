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
    INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),

    // User
    EMAIL_NOT_FOUND(400, "M000", "Email is not Exist"),
    EMAIL_DUPLICATION(400, "M001", "Email is Duplication"),
    LOGIN_INPUT_INVALID(400, "M002", "Login input is invalid"),
    USER_NOT_FOUND(400, "M003", "User is not Exist"),
    NICKNAME_EMAIL_DUPLICATION(400, "M004", "NickName is Duplication"),
    USER_NOT_AUTHENTICATION(401, "M005", "User is not Authentication"),
    SOCIAL_LOGIN_TOKEN_ERROR(401, "M006", "Social Login is Token Error"),
    SOCIAL_TYPE_NOT_FOUND(400, "M007", "Social Type is not Exist"),

    // Bucket
    BUCKET_NOT_FOUND(400, "B001", "Bucket is not Exist"),

    // Coupon
    COUPON_ALREADY_USE(400, "CO001", "Coupon was already used"),
    COUPON_EXPIRE(400, "CO002", "Coupon was already expired")
    ;

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
