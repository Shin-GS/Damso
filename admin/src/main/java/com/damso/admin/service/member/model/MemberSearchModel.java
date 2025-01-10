package com.damso.admin.service.member.model;

import com.damso.core.enums.member.MemberRoleType;
import com.damso.core.enums.member.MemberStatusType;
import com.damso.domain.db.entity.member.Member;

import java.time.LocalDate;

public record MemberSearchModel(Long memberId,
                                String email,
                                String name,
                                MemberRoleType role,
                                MemberStatusType status,
                                LocalDate joinDate) {
    public static MemberSearchModel of(Member member) {
        return new MemberSearchModel(member.getId(),
                member.getEmail(),
                member.getName(),
                member.getRole(),
                member.getStatus(),
                member.getCreatedAt().toLocalDate());
    }
}
