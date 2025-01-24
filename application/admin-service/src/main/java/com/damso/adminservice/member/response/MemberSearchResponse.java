package com.damso.adminservice.member.response;

import com.damso.core.enums.member.MemberRoleType;
import com.damso.core.enums.member.MemberStatusType;
import com.damso.storage.entity.member.Member;

import java.time.LocalDate;

public record MemberSearchResponse(Long memberId,
                                   String email,
                                   String name,
                                   MemberRoleType role,
                                   MemberStatusType status,
                                   LocalDate joinDate) {
    public static MemberSearchResponse of(Member member) {
        return new MemberSearchResponse(member.getId(),
                member.getEmail(),
                member.getName(),
                member.getRole(),
                member.getStatus(),
                member.getCreatedAt().toLocalDate());
    }
}
