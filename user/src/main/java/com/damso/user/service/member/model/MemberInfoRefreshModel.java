package com.damso.user.service.member.model;

import com.damso.domain.db.entity.member.Member;

public record MemberInfoRefreshModel(String name,
                                     String email) {
    public static MemberInfoRefreshModel of(Member member) {
        return new MemberInfoRefreshModel(
                member.getName(),
                member.getEmail()
        );
    }
}
