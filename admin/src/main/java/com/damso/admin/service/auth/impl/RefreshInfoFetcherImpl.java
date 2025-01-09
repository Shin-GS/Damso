package com.damso.admin.service.auth.impl;

import com.damso.admin.service.auth.RefreshInfoFetcher;
import com.damso.admin.service.auth.model.RefreshInfoModel;
import com.damso.core.response.error.ErrorCode;
import com.damso.core.response.exception.BusinessException;
import com.damso.domain.db.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefreshInfoFetcherImpl implements RefreshInfoFetcher {
    private final MemberRepository memberRepository;

    @Override
    public RefreshInfoModel refresh(Long memberId) {
        return memberRepository.findById(memberId)
                .map(RefreshInfoModel::of)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
