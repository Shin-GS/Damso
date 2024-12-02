package com.damso.admin.service.member;

import com.damso.admin.service.member.command.MemberSearchCommand;
import com.damso.admin.service.member.model.MemberSearchModel;
import com.damso.admin.service.member.command.MemberRegisterCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberFinder {
    Page<MemberSearchModel> findMembers(MemberSearchCommand command,
                                        Pageable pageable);

    void register(MemberRegisterCommand command);
}
