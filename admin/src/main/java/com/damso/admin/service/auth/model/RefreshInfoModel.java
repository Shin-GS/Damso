package com.damso.admin.service.auth.model;

import com.damso.domain.db.entity.member.Member;

public record RefreshInfoModel(String name) {
    public static RefreshInfoModel of(Member member) {
        return new RefreshInfoModel(
                member.getName()
        );
    }
}
