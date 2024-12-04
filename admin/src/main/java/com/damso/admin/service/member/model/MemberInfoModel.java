package com.damso.admin.service.member.model;

import com.damso.core.constant.MemberRoleType;
import com.damso.core.constant.MemberStatusType;
import com.damso.repository.db.entity.member.Member;

public record MemberInfoModel(Long memberId,
                              String email,
                              String name,
                              MemberRoleType role,
                              MemberStatusType status) {
    public static MemberInfoModel of(Member member) {
        return new MemberInfoModel(member.getId(),
                member.getEmail(),
                member.getName(),
                member.getRole(),
                member.getStatus());
    }
}
