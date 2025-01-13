package com.damso.user.service.auth.command;

import com.damso.core.request.regex.ValidPattern;
import com.damso.core.request.regex.pattern.MemberRegexPattern;

public record EmailDuplicationCommand(
        @ValidPattern(value = MemberRegexPattern.class, fieldCode = "EMAIL", notEmpty = true) String email) {
}
