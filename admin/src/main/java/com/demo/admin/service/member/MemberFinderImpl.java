package com.demo.admin.service.member;

import com.demo.admin.core.response.error.ErrorCode;
import com.demo.admin.core.response.exception.BusinessException;
import com.demo.admin.service.member.command.MemberRegisterCommand;
import com.demo.admin.service.member.command.MemberSearchCommand;
import com.demo.admin.service.member.model.MemberSearchModel;
import com.demo.admin.storage.entity.member.Member;
import com.demo.admin.storage.repository.member.MemberRepository;
import com.demo.admin.storage.repository.member.MemberRepositorySupport;
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
    public Page<MemberSearchModel> findMembers(MemberSearchCommand command,
                                               Pageable pageable) {
        return memberRepositorySupport.findAllMembers(command, pageable)
                .map(MemberSearchModel::of);
    }

    @Override
    public void register(MemberRegisterCommand command) {
        if (memberRepository.existsByEmail(command.email())) {
            throw new BusinessException(ErrorCode.MEMBER_EMAIL_DUPLICATED);
        }

        memberRepository.save(new Member(
                command.email(),
                command.name(),
                command.role())
        );
    }
}
