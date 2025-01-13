package com.damso.user.service.auth.impl;

import com.damso.core.response.error.ErrorCode;
import com.damso.core.response.exception.BusinessException;
import com.damso.domain.db.repository.member.MemberRepository;
import com.damso.user.service.auth.RefreshInfoFetcher;
import com.damso.user.service.auth.response.RefreshInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefreshInfoFetcherImpl implements RefreshInfoFetcher {
    private final MemberRepository memberRepository;

    @Override
    public RefreshInfoResponse refresh(Long memberId) {
        return memberRepository.findById(memberId)
                .map(RefreshInfoResponse::of)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
