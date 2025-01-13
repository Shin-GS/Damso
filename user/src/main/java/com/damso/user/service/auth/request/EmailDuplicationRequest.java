package com.damso.user.service.auth.request;

import com.damso.core.request.regex.ValidPattern;
import com.damso.core.request.regex.pattern.MemberRegexPattern;

public record EmailDuplicationRequest(
        @ValidPattern(value = MemberRegexPattern.class, fieldCode = "EMAIL", notEmpty = true) String email) {
}
