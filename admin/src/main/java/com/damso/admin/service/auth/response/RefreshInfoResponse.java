package com.damso.admin.service.auth.response;

import com.damso.domain.db.entity.member.Member;

public record RefreshInfoResponse(String name) {
    public static RefreshInfoResponse of(Member member) {
        return new RefreshInfoResponse(
                member.getName()
        );
    }
}
