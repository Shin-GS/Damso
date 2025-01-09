package com.damso.user.service.member;

import com.damso.domain.db.entity.member.Member;

public interface MemberFinder {
    Member getEntity(Long memberId);
}
