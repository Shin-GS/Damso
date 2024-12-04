package com.damso.core.response.success;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SuccessCode {
    SUCCESS(200, "S001", "Success"),
    ;

    private final int status;
    private final String code;
    private final String message;

    SuccessCode(int status,
                String code,
                String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
