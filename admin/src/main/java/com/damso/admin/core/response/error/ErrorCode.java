package com.damso.admin.core.response.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    //I : 사용자의 입력 관련
    INVALID_INPUT_VALUE(400, "I001", "Invalid Input Value"),

    //D : DB 관련
    ENTITY_NOT_FOUND(400, "D001", "entity not found"),

    //B : 비즈니스 로직 관련
    MEMBER_EMAIL_DUPLICATED(400, "B001", "중복된 이메일입니다."),

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
