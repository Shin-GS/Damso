package com.damso.core.response.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    //I : 사용자의 입력 관련
    INVALID_INPUT_VALUE(400, "I001", "Invalid Input Value"),
    FILE_EMPTY(400, "I002", "File is Empty"),
    FILE_SIZE_EXCEEDED(400, "I003", "File Size Exceeded"),
    FILE_EXTENSION_NOT_ALLOWED(400, "I004", "File Extension Not Allowed"),
    FILE_TYPE_NOT_ALLOWED(400, "I005", "File Type Not Allowed"),
    FILE_NAME_INVALID(400, "I006", "File Name Is Invalid"),

    //T : 사용자 토큰 관련
    TOKEN_EXPIRED(400, "T001", "인증번호 유효기간이 만료되었습니다."),
    REFRESH_TOKEN_NOT_FOUND(401, "T002", "Refresh Token Not Found"),
    REFRESH_TOKEN_INVALID(401, "T003", "Refresh Token is invalid"),
    ACCESS_DENIED(403, "T004", "잘못된 접근입니다."),

    //D : DB 관련
    ENTITY_NOT_FOUND(400, "D001", "entity not found"),
    MEMBER_NOT_FOUND(400, "D002", "존재하지 않는 회원입니다."),

    //B : 비즈니스 로직 관련
    MEMBER_EMAIL_DUPLICATED(400, "B001", "중복된 이메일입니다."),
    MEMBER_SNS_DUPLICATED(400, "B002", "중복된 SNS 계정입니다."),
    OAUTH_INFO_NOT_VALID(400, "B003", "OAuth 정보가 유효하지 않습니다."),
    PASSWORD_NOT_MATCHED(400, "B004", "비밀번호가 일치하지 않습니다."),
    MEMBER_NOT_ACTIVE(400, "B005", "탈퇴 혹은 차단된 회원입니다."),

    //S : 서버에러
    INTERNAL_SERVER_ERROR(500, "S001", "internal server error");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status,
              String code,
              String message) {
        this.message = message;
        this.code = code;
        this.status = status;
    }
}
