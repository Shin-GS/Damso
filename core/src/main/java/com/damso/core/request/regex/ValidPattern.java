package com.damso.core.request.regex;

import com.damso.core.request.regex.pattern.CommonRegexPattern;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidPatternValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPattern {
    Class<? extends CommonRegexPattern> value(); // CommonRegexPattern을 구현한 Enum 클래스

    String fieldCode(); // 검증하기 원하는 코드 값

    boolean notEmpty() default false; // 빈 값 허용 여부(null or blank)

    String message() default ""; // 메시지는 Enum에 정의된 것을 사용하기 때문에 기본 메시지는 의미가 없음(프레임워크에서 필요해서 정의)

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
