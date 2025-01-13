package com.damso.user.service.auth.response;

import com.damso.core.enums.member.MemberRoleType;
import com.damso.domain.db.entity.member.Member;

public record RefreshInfoResponse(String name,
                                  String email,
                                  MemberRoleType role) {
    public static RefreshInfoResponse of(Member member) {
        return new RefreshInfoResponse(
                member.getName(),
                member.getEmail(),
                member.getRole()
        );
    }
}
