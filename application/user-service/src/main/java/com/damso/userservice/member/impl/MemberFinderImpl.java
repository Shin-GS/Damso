package com.damso.userservice.member.impl;

import com.damso.core.enums.member.MemberSocialAccountType;
import com.damso.core.code.ErrorCode;
import com.damso.core.exception.BusinessException;
import com.damso.storage.entity.member.Member;
import com.damso.storage.entity.member.MemberSocialAccount;
import com.damso.storage.repository.member.MemberRepository;
import com.damso.storage.repository.member.MemberSocialAccountRepository;
import com.damso.userservice.member.MemberFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberFinderImpl implements MemberFinder {
    private final MemberRepository memberRepository;
    private final MemberSocialAccountRepository memberSocialAccountRepository;

    @Override
    public Member getEntity(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
    }

    @Override
    public Optional<Long> findMemberId(MemberSocialAccountType provider, String providerAccountId) {
        return memberSocialAccountRepository.findByProviderAndProviderAccountId(provider, providerAccountId)
                .map(MemberSocialAccount::getMemberId);
    }
}
