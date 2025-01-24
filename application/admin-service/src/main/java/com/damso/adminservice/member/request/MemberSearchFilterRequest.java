package com.damso.adminservice.member.request;

import com.damso.core.enums.member.MemberRoleType;
import com.damso.core.enums.member.MemberStatusType;

import java.time.LocalDate;

public record MemberSearchFilterRequest(Long memberId,
                                        String email,
                                        String name,
                                        MemberRoleType role,
                                        MemberStatusType status,
                                        LocalDate startDate,
                                        LocalDate endDate) {
}
