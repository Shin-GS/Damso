package com.damso.admin.service.member.impl;

import com.damso.admin.service.member.MemberFinder;
import com.damso.admin.service.member.command.MemberSearchFilterCommand;
import com.damso.admin.service.member.model.MemberInfoModel;
import com.damso.admin.service.member.model.MemberSearchModel;
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
    public Page<MemberSearchModel> findMembers(MemberSearchFilterCommand command,
                                               Pageable pageable) {
        return memberRepositorySupport.findAllMembers(
                        command.memberId(),
                        command.email(),
                        command.name(),
                        command.role(),
                        command.status(),
                        command.startDate(),
                        command.endDate(),
                        pageable)
                .map(MemberSearchModel::of);
    }

    @Override
    public MemberInfoModel get(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        return MemberInfoModel.of(member);
    }
}
