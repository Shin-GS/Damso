package com.damso.core.response.exception;

import com.damso.core.response.error.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private ErrorCode code;
    private String field;
    private String value;
    private String reason;

    public BusinessException(String message,
                             Throwable throwable,
                             ErrorCode code) {
        super(message, throwable);
        this.code = code;
    }

    public BusinessException(String message,
                             ErrorCode code) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }
}
