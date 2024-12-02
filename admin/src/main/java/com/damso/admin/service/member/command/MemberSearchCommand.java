package com.damso.admin.service.member.command;

import java.time.LocalDate;

public record MemberSearchCommand(Long memberId,
                                  String email,
                                  String name,
                                  LocalDate startDate,
                                  LocalDate endDate) {
    public static MemberSearchCommand ofEmpty() {
        return new MemberSearchCommand(null,
                null,
                null,
                null,
                null);
    }
}
