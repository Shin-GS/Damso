package com.damso.core.response.error;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
    private String message;
    private int status;
    private String code;
    private Set<FieldError> error;

    private ErrorResponse(String message,
                          int status,
                          String code,
                          Set<FieldError> error) {
        this.message = message;
        this.status = status;
        this.code = code;
        this.error = error;
    }

    public static ErrorResponse of(ErrorCode code) {
        return new ErrorResponse(code.getMessage(),
                code.getStatus(),
                code.getCode(),
                Set.of());
    }

    public static ErrorResponse of(ErrorCode code,
                                   String message) {
        return new ErrorResponse(message,
                code.getStatus(),
                code.getCode(),
                Set.of());
    }

    public static ErrorResponse of(ErrorCode code,
                                   BindingResult bindingResult) {
        Set<FieldError> fieldErrors = bindingResult.getFieldErrors().stream()
                .map(error -> new FieldError(error.getField(),
                        !ObjectUtils.isEmpty(error.getRejectedValue()) ? error.getRejectedValue().toString() : "",
                        error.getDefaultMessage()))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return new ErrorResponse(code.getMessage(),
                code.getStatus(),
                code.getCode(),
                fieldErrors);
    }

    public String getFirstMessage() {
        return this.getError().stream()
                .findFirst()
                .map(ErrorResponse.FieldError::getReason)
                .orElse(ErrorCode.INVALID_INPUT_VALUE.getMessage());
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @EqualsAndHashCode(of = {"field", "value", "reason"})
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        public FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }
    }
}
