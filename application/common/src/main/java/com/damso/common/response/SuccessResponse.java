package com.damso.common.response;

import com.damso.core.code.SuccessCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SuccessResponse<T> {
    private String message;
    private int status;
    private String code;
    private T result;

    private SuccessResponse(String message,
                            int status,
                            String code,
                            T result) {
        this.message = message;
        this.status = status;
        this.code = code;
        this.result = result;
    }

    public static SuccessResponse<Object> of(SuccessCode code) {
        return new SuccessResponse<>(code.getMessage(), code.getStatus(), code.getCode(), null);
    }

    public static <T> SuccessResponse<T> of(SuccessCode code,
                                            T result) {
        return new SuccessResponse<>(code.getMessage(), code.getStatus(), code.getCode(), result);
    }
}
