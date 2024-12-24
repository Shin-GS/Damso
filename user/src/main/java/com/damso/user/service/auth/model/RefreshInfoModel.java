package com.damso.user.service.auth.model;

import com.damso.domain.db.entity.member.Member;

public record RefreshInfoModel(String name,
                               String email) {
    public static RefreshInfoModel of(Member member) {
        return new RefreshInfoModel(
                member.getName(),
                member.getEmail()
        );
    }
}
