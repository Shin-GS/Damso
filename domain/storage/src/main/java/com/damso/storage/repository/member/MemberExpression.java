package com.damso.storage.repository.member;

import com.damso.core.enums.member.MemberRoleType;
import com.damso.core.enums.member.MemberStatusType;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

import static com.damso.storage.entity.member.QMember.member;

public class MemberExpression {
    private MemberExpression() {
    }

    public static BooleanExpression memberIdEq(Long memberId) {
        return memberId == null ? null : member.id.eq(memberId);
    }

    public static BooleanExpression emailEq(String email) {
        return !StringUtils.hasText(email) ? null : member.email.eq(email);
    }

    public static BooleanExpression emailContains(String email) {
        return !StringUtils.hasText(email) ? null : member.email.contains(email);
    }

    public static BooleanExpression nameEq(String name) {
        return !StringUtils.hasText(name) ? null : member.name.eq(name);
    }

    public static BooleanExpression nameContains(String name) {
        return !StringUtils.hasText(name) ? null : member.name.contains(name);
    }

    public static BooleanExpression roleEq(MemberRoleType role) {
        return role == null ? null : member.role.eq(role);
    }

    public static BooleanExpression statusEq(MemberStatusType status) {
        return status == null ? null : member.status.eq(status);
    }

    public static BooleanExpression joinedAtGoe(LocalDate date) {
        return date == null ? null : member.createdAt.goe(date.atStartOfDay());
    }

    public static BooleanExpression joinedAtLoe(LocalDate date) {
        return date == null ? null : member.createdAt.loe(date.atStartOfDay());
    }
}
