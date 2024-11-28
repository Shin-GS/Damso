package com.demo.admin.service.member;

import com.demo.admin.service.member.command.MemberRegisterCommand;
import com.demo.admin.service.member.command.MemberSearchCommand;
import com.demo.admin.service.member.model.MemberSearchModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberFinder {
    Page<MemberSearchModel> findMembers(MemberSearchCommand command,
                                        Pageable pageable);

    void register(MemberRegisterCommand command);
}
