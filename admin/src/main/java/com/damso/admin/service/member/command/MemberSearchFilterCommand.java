package com.damso.admin.service.member.command;

import com.damso.core.constant.MemberRoleType;
import com.damso.core.constant.MemberStatusType;

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
