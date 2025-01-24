package com.damso.adminservice.auth.impl;

import com.damso.adminservice.auth.RefreshInfoFetcher;
import com.damso.adminservice.auth.response.RefreshInfoResponse;
import com.damso.core.code.ErrorCode;
import com.damso.core.exception.BusinessException;
import com.damso.storage.repository.member.MemberRepository;
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
