package com.damso.admin.service.member.impl;

import com.damso.admin.service.member.MemberFinder;
import com.damso.admin.service.member.request.MemberSearchFilterRequest;
import com.damso.admin.service.member.response.MemberInfoResponse;
import com.damso.admin.service.member.response.MemberSearchResponse;
import com.damso.core.response.error.ErrorCode;
import com.damso.core.response.exception.BusinessException;
import com.damso.domain.db.entity.member.Member;
import com.damso.domain.db.repository.member.MemberRepository;
import com.damso.domain.db.repository.member.MemberRepositorySupport;
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
