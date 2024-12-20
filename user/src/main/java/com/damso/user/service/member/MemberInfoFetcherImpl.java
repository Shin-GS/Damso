package com.damso.user.service.member;

import com.damso.core.response.error.ErrorCode;
import com.damso.core.response.exception.BusinessException;
import com.damso.domain.db.repository.member.MemberRepository;
import com.damso.user.service.member.model.MemberInfoRefreshModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberInfoFetcherImpl implements MemberInfoFetcher {
    private final MemberRepository memberRepository;

    @Override
    public MemberInfoRefreshModel refresh(Long memberId) {
        return memberRepository.findById(memberId)
                .map(MemberInfoRefreshModel::of)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
