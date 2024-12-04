//package com.damso.repository.db.repository.member;
//
//import com.damso.admin.core.constant.MemberRoleType;
//import com.damso.admin.core.constant.MemberStatusType;
//import com.querydsl.core.types.dsl.BooleanExpression;
//import org.springframework.util.StringUtils;
//
//import java.time.LocalDate;
//
//import static com.damso.admin.storage.entity.member.QMember.member;
//
//public class MemberExpression {
//    private MemberExpression() {
//    }
//
//    public static BooleanExpression memberIdEq(Long memberId) {
//        return memberId == null ? null : QMember.member.id.eq(memberId);
//    }
//
//    public static BooleanExpression emailEq(String email) {
//        return !StringUtils.hasText(email) ? null : QMember.member.email.eq(email);
//    }
//
//    public static BooleanExpression emailLike(String email) {
//        return !StringUtils.hasText(email) ? null : QMember.member.email.like(email);
//    }
//
//    public static BooleanExpression nameEq(String name) {
//        return !StringUtils.hasText(name) ? null : QMember.member.name.eq(name);
//    }
//
//    public static BooleanExpression nameLike(String name) {
//        return !StringUtils.hasText(name) ? null : QMember.member.name.like(name);
//    }
//
//    public static BooleanExpression roleEq(MemberRoleType role) {
//        return role == null ? null : QMember.member.role.eq(role);
//    }
//
//    public static BooleanExpression statusEq(MemberStatusType status) {
//        return status == null ? null : QMember.member.status.eq(status);
//    }
//
//    public static BooleanExpression joinedAtGoe(LocalDate date) {
//        return date == null ? null : QMember.member.createdAt.goe(date.atStartOfDay());
//    }
//
//    public static BooleanExpression joinedAtLoe(LocalDate date) {
//        return date == null ? null : QMember.member.createdAt.loe(date.atStartOfDay());
//    }
//}
