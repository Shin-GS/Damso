package com.damso.userservice.auth.request;

import com.damso.common.request.ValidPattern;
import com.damso.common.request.pattern.MemberRegexPattern;

public record EmailSignupRequest(@ValidPattern(value = MemberRegexPattern.class, fieldCode = "NAME", notEmpty = true)
                                 String name,

                                 @ValidPattern(value = MemberRegexPattern.class, fieldCode = "EMAIL", notEmpty = true)
                                 String email,

                                 @ValidPattern(value = MemberRegexPattern.class, fieldCode = "PASSWORD", notEmpty = true)
                                 String password,

                                 @ValidPattern(value = MemberRegexPattern.class, fieldCode = "TERMS_AGREED", notEmpty = true)
                                 Boolean termsAgreed) {
}
