package com.damso.core.request.regex;

import com.damso.core.request.regex.pattern.CommonRegexPattern;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.ObjectUtils;

import java.util.stream.Stream;

public class ValidPatternValidator implements ConstraintValidator<ValidPattern, Object> {
    private CommonRegexPattern regexPattern;
    private boolean notEmpty;

    @Override
    public void initialize(ValidPattern constraintAnnotation) {
        Class<? extends CommonRegexPattern> enumClass = constraintAnnotation.value();
        String fieldCode = constraintAnnotation.fieldCode();
        this.regexPattern = Stream.of(enumClass.getEnumConstants())
                .filter(pattern -> pattern.getCode().equals(fieldCode)) // Enum에서 패턴 찾기
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid fieldCode: " + fieldCode));
        this.notEmpty = constraintAnnotation.notEmpty();
    }

    @Override
    public boolean isValid(Object requestValue,
                           ConstraintValidatorContext context) {
        // not empty check
        if (notEmpty && ObjectUtils.isEmpty(requestValue)) {
            return failValidation(context, regexPattern.getMessage());
        }

        // empty is okay
        if (ObjectUtils.isEmpty(requestValue)) {
            return Boolean.TRUE;
        }

        if (requestValue instanceof Boolean boolValue) {
            return validateBoolean(boolValue, context);
        } else if (requestValue instanceof String strValue) {
            return validateString(strValue, context);
        }

        return Boolean.TRUE;
    }

    private boolean validateBoolean(Boolean requestValue,
                                    ConstraintValidatorContext context) {
        if (requestValue.equals(Boolean.parseBoolean(regexPattern.getPattern()))) {
            return Boolean.TRUE;
        }

        return failValidation(context, regexPattern.getMessage());
    }

    private boolean validateString(String requestValue,
                                   ConstraintValidatorContext context) {
        if (requestValue.matches(regexPattern.getPattern())) {
            return Boolean.TRUE;
        }

        return failValidation(context, regexPattern.getMessage());
    }

    private boolean failValidation(ConstraintValidatorContext context,
                                   String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
        return Boolean.FALSE;
    }
}
