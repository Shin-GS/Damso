package com.damso.userservice.auth.response;

import com.damso.core.enums.member.MemberRoleType;
import com.damso.storage.entity.member.Member;

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
