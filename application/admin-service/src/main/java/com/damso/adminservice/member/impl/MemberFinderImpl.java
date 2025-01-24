package com.damso.adminservice.member.impl;

import com.damso.adminservice.member.MemberFinder;
import com.damso.adminservice.member.request.MemberSearchFilterRequest;
import com.damso.adminservice.member.response.MemberInfoResponse;
import com.damso.adminservice.member.response.MemberSearchResponse;
import com.damso.core.code.ErrorCode;
import com.damso.core.exception.BusinessException;
import com.damso.storage.entity.member.Member;
import com.damso.storage.repository.member.MemberRepository;
import com.damso.storage.repository.member.MemberRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberFinderImpl implements MemberFinder {
    private final MemberRepositorySupport memberRepositorySupport;
    private final MemberRepository memberRepository;

    @Override
    public Page<MemberSearchResponse> findMembers(MemberSearchFilterRequest request,
                                                  Pageable pageable) {
        return memberRepositorySupport.findAllMembers(
                        request.memberId(),
                        request.email(),
                        request.name(),
                        request.role(),
                        request.status(),
                        request.startDate(),
                        request.endDate(),
                        pageable)
                .map(MemberSearchResponse::of);
    }

    @Override
    public MemberInfoResponse get(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        return MemberInfoResponse.of(member);
    }
}
