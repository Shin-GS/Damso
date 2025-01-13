package com.damso.user.service.auth.request;

import com.damso.core.request.regex.ValidPattern;
import com.damso.core.request.regex.pattern.MemberRegexPattern;

public record EmailLoginRequest(@ValidPattern(value = MemberRegexPattern.class, fieldCode = "EMAIL", notEmpty = true)
                                String email,

                                @ValidPattern(value = MemberRegexPattern.class, fieldCode = "PASSWORD", notEmpty = true)
                                String password) {
}
