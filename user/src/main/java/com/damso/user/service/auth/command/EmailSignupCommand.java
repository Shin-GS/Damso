package com.damso.user.service.auth.command;

import com.damso.core.request.regex.ValidPattern;
import com.damso.core.request.regex.pattern.MemberRegexPattern;

public record EmailSignupCommand(@ValidPattern(value = MemberRegexPattern.class, fieldCode = "NAME", notEmpty = true)
                                 String name,

                                 @ValidPattern(value = MemberRegexPattern.class, fieldCode = "EMAIL", notEmpty = true)
                                 String email,

                                 @ValidPattern(value = MemberRegexPattern.class, fieldCode = "PASSWORD", notEmpty = true)
                                 String password,

                                 @ValidPattern(value = MemberRegexPattern.class, fieldCode = "TERMS_AGREED", notEmpty = true)
                                 Boolean termsAgreed) {
}
