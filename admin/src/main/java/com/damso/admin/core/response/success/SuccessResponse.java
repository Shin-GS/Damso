package com.damso.admin.core.response.success;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SuccessResponse {
    private String message;
    private int status;
    private String code;
    private Object result;

    private SuccessResponse(String message,
                            int status,
                            String code,
                            Object result) {
        this.message = message;
        this.status = status;
        this.code = code;
        this.result = result;
    }

    public static SuccessResponse of(SuccessCode code) {
        return new SuccessResponse(code.getMessage(), code.getStatus(), code.getCode(), null);
    }

    public static SuccessResponse of(SuccessCode code,
                                     Object result) {
        return new SuccessResponse(code.getMessage(), code.getStatus(), code.getCode(), result);
    }
}
