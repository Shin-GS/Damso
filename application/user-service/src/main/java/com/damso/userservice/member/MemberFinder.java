package com.damso.userservice.member;

import com.damso.core.enums.member.MemberSocialAccountType;
import com.damso.storage.entity.member.Member;

import java.util.Optional;

public interface MemberFinder {
    Member getEntity(Long memberId);

    Optional<Long> findMemberId(MemberSocialAccountType provider,
                                String providerAccountId);
}
