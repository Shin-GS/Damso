package com.damso.domain.db.repository.member;

import com.damso.core.constant.MemberRoleType;
import com.damso.core.constant.MemberStatusType;
import com.damso.domain.db.entity.member.Member;
import com.damso.domain.db.entity.member.QMember;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public Page<Member> findAllMembers(Long memberId,
                                       String email,
                                       String name,
                                       MemberRoleType role,
                                       MemberStatusType status,
                                       LocalDate startDate,
                                       LocalDate endDate,
                                       Pageable pageable) {
        List<Member> members = queryFactory.select(QMember.member)
                .from(QMember.member)
                .where(
                        MemberExpression.memberIdEq(memberId),
                        MemberExpression.emailContains(email),
                        MemberExpression.nameContains(name),
                        MemberExpression.roleEq(role),
                        MemberExpression.statusEq(status),
                        MemberExpression.joinedAtGoe(startDate),
                        MemberExpression.joinedAtLoe(endDate)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(QMember.member.id.desc())
                .fetch();

        JPAQuery<Long> total = queryFactory.select(QMember.member.count())
                .from(QMember.member)
                .where(
                        MemberExpression.memberIdEq(memberId),
                        MemberExpression.emailContains(email),
                        MemberExpression.nameContains(name),
                        MemberExpression.roleEq(role),
                        MemberExpression.statusEq(status),
                        MemberExpression.joinedAtGoe(startDate),
                        MemberExpression.joinedAtLoe(endDate)
                );

        return PageableExecutionUtils.getPage(members, pageable, total::fetchOne);
    }
}
