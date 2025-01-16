package com.damso.core.request.regex.pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberRegexPattern implements CommonRegexPattern {
    EMAIL("EMAIL", "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", "유효한 이메일 주소를 입력하세요."),
    NAME("NAME", "^[a-zA-Z가-힣]{2,50}(-[a-zA-Z가-힣]{2,50})?$", "^[a-zA-Z가-힣]{2,50}(-[a-zA-Z가-힣]{2,50})?$", "이름은 2~50자 사이의 한글, 영문, 또는 '-'로만 구성될 수 있습니다."),
    PASSWORD("PASSWORD", "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,64}$", "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,64}$", "비밀번호는 8~64자 사이로 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다."),
    TERMS_AGREED("TERMS_AGREED", "TRUE", "TRUE", "이용약관에 동의해주세요.");

    private final String code;
    private final String pattern;
    private final String fePattern;
    private final String message;
}
