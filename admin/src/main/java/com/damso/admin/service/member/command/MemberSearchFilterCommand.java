package com.damso.admin.service.member.command;

import com.damso.core.enums.member.MemberRoleType;
import com.damso.core.enums.member.MemberStatusType;

import java.time.LocalDate;

public record MemberSearchFilterCommand(Long memberId,
                                        String email,
                                        String name,
                                        MemberRoleType role,
                                        MemberStatusType status,
                                        LocalDate startDate,
                                        LocalDate endDate) {
    public static MemberSearchFilterCommand ofEmpty() {
        return new MemberSearchFilterCommand(null,
                null,
                null,
                null,
                null,
                null,
                null);
    }
}
