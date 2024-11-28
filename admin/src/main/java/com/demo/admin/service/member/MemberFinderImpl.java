package com.demo.admin.service.member;

import com.demo.admin.service.member.command.MemberSearchCommand;
import com.demo.admin.service.member.model.MemberSearchModel;
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

    @Override
    public Page<MemberSearchModel> findMembers(MemberSearchCommand command,
                                               Pageable pageable) {
        return memberRepositorySupport.findAllMembers(command, pageable)
                .map(MemberSearchModel::of);
    }
}
