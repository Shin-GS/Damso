package com.damso.core.exception;

import com.damso.core.code.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCode code;

    public BusinessException(String message,
                             Throwable throwable,
                             ErrorCode code) {
        super(message, throwable);
        this.code = code;
    }

    public BusinessException(ErrorCode code,
                             String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }
}
