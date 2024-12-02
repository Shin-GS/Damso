package com.damso.admin.service.member;

import com.damso.admin.service.member.command.MemberModifyCommand;
import com.damso.admin.service.member.command.MemberRegisterCommand;
import com.damso.admin.service.member.command.MemberSearchCommand;
import com.damso.admin.service.member.model.MemberInfoModel;
import com.damso.admin.service.member.model.MemberSearchModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberEditor {
    Page<MemberSearchModel> findMembers(MemberSearchCommand command,
                                        Pageable pageable);

    void register(MemberRegisterCommand command);

    MemberInfoModel get(Long memberId);

    void modify(Long memberId, MemberModifyCommand command);
}
