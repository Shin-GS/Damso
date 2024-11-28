package com.demo.admin.storage.repository.member;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

import static com.demo.admin.storage.entity.member.QMember.member;

public class MemberExpression {
    private MemberExpression() {
    }

    public static BooleanExpression idEq(Long memberId) {
        return memberId == null ? null : member.id.eq(memberId);
    }

    public static BooleanExpression emailEq(String email) {
        return !StringUtils.hasText(email) ? null : member.email.eq(email);
    }

    public static BooleanExpression emailLike(String email) {
        return !StringUtils.hasText(email) ? null : member.email.like(email);
    }

    public static BooleanExpression nameEq(String name) {
        return !StringUtils.hasText(name) ? null : member.name.eq(name);
    }

    public static BooleanExpression nameLike(String name) {
        return !StringUtils.hasText(name) ? null : member.name.like(name);
    }

    public static BooleanExpression joinedAtGoe(LocalDate date) {
        return date == null ? null : member.createdAt.goe(date.atStartOfDay());
    }

    public static BooleanExpression joinedAtLoe(LocalDate date) {
        return date == null ? null : member.createdAt.loe(date.atStartOfDay());
    }
}
