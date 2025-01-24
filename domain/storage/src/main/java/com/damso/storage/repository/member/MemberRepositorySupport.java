package com.damso.storage.repository.member;

import com.damso.core.enums.member.MemberRoleType;
import com.damso.core.enums.member.MemberStatusType;
import com.damso.storage.entity.member.Member;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.damso.storage.entity.member.QMember.member;

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
        List<Member> members = queryFactory.select(member)
                .from(member)
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
                .orderBy(member.id.desc())
                .fetch();

        JPAQuery<Long> total = queryFactory.select(member.count())
                .from(member)
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
