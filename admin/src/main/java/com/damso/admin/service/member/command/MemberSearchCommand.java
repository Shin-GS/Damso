package com.damso.admin.service.member.command;

import com.damso.core.constant.MemberRoleType;
import com.damso.core.constant.MemberStatusType;

import java.time.LocalDate;

public record MemberSearchCommand(Long memberId,
                                  String email,
                                  String name,
                                  MemberRoleType role,
                                  MemberStatusType status,
                                  LocalDate startDate,
                                  LocalDate endDate) {
    public static MemberSearchCommand ofEmpty() {
        return new MemberSearchCommand(null,
                null,
                null,
                null,
                null,
                null,
                null);
    }
}
