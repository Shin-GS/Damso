package com.damso.admin.service.member.model;

import com.damso.admin.storage.entity.member.Member;

import java.time.LocalDate;

public record MemberSearchModel(Long memberId,
                                String email,
                                String name,
                                LocalDate joinDate) {
    public static MemberSearchModel of(Member member) {
        return new MemberSearchModel(member.getId(),
                member.getEmail(),
                member.getName(),
                member.getCreatedAt().toLocalDate());
    }
}
