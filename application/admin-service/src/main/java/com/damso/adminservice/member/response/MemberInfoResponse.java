package com.damso.adminservice.member.response;

import com.damso.core.enums.member.MemberRoleType;
import com.damso.core.enums.member.MemberStatusType;
import com.damso.storage.entity.member.Member;

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
