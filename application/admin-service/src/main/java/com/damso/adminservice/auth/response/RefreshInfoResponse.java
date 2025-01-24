package com.damso.adminservice.auth.response;

import com.damso.storage.entity.member.Member;

public record RefreshInfoResponse(String name) {
    public static RefreshInfoResponse of(Member member) {
        return new RefreshInfoResponse(
                member.getName()
        );
    }
}
