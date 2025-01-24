package com.damso.adminservice.member.impl;

import com.damso.adminservice.member.MemberEditor;
import com.damso.adminservice.member.request.MemberModifyRequest;
import com.damso.adminservice.member.request.MemberRegisterRequest;
import com.damso.core.code.ErrorCode;
import com.damso.core.exception.BusinessException;
import com.damso.storage.entity.member.Member;
import com.damso.storage.repository.member.MemberRepository;
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
