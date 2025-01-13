package com.damso.admin.service.member.response;

import com.damso.core.enums.member.MemberRoleType;
import com.damso.core.enums.member.MemberStatusType;
import com.damso.domain.db.entity.member.Member;

public record MemberInfoResponse(Long memberId,
                                 String email,
                                 String name,
                                 MemberRoleType role,
                                 MemberStatusType status) {
    public static MemberInfoResponse of(Member member) {
        return new MemberInfoResponse(member.getId(),
                member.getEmail(),
                member.getName(),
                member.getRole(),
                member.getStatus());
    }
}
