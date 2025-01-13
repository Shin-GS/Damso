package com.damso.admin.service.member.impl;

import com.damso.admin.service.member.MemberEditor;
import com.damso.admin.service.member.request.MemberModifyRequest;
import com.damso.admin.service.member.request.MemberRegisterRequest;
import com.damso.core.response.error.ErrorCode;
import com.damso.core.response.exception.BusinessException;
import com.damso.domain.db.entity.member.Member;
import com.damso.domain.db.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberEditorImpl implements MemberEditor {
    private final MemberRepository memberRepository;

    @Override
    public void register(MemberRegisterRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new BusinessException(ErrorCode.MEMBER_EMAIL_DUPLICATED);
        }

//        memberRepository.save(new Member(
//                request.email(),
//                request.name(),
//                request.role())
//        );
    }

    @Override
    public void modify(Long memberId, MemberModifyRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        if (!member.getEmail().equals(request.email()) && memberRepository.existsByEmail(request.email())) {
            throw new BusinessException(ErrorCode.MEMBER_EMAIL_DUPLICATED);
        }

//        member.update(request.email(),
//                request.name(),
//                request.role(),
//                request.status());
    }
}
