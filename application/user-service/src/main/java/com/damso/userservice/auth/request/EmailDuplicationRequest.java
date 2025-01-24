package com.damso.userservice.auth.request;

import com.damso.common.request.ValidPattern;
import com.damso.common.request.pattern.MemberRegexPattern;

public record EmailDuplicationRequest(
        @ValidPattern(value = MemberRegexPattern.class, fieldCode = "EMAIL", notEmpty = true) String email) {
}
