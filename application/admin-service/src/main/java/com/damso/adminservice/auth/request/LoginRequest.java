package com.damso.adminservice.auth.request;

import com.damso.common.request.ValidPattern;
import com.damso.common.request.pattern.MemberRegexPattern;

public record LoginRequest(@ValidPattern(value = MemberRegexPattern.class, fieldCode = "EMAIL", notEmpty = true)
                           String email,

                           @ValidPattern(value = MemberRegexPattern.class, fieldCode = "PASSWORD", notEmpty = true)
                           String password) {
}
