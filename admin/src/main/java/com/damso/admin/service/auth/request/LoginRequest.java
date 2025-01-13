package com.damso.admin.service.auth.request;

import com.damso.core.request.regex.ValidPattern;
import com.damso.core.request.regex.pattern.MemberRegexPattern;

public record LoginRequest(@ValidPattern(value = MemberRegexPattern.class, fieldCode = "EMAIL", notEmpty = true)
                           String email,

                           @ValidPattern(value = MemberRegexPattern.class, fieldCode = "PASSWORD", notEmpty = true)
                           String password) {
}
