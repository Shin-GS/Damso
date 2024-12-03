package com.damso.admin.service.member;

import com.damso.admin.service.member.command.MemberModifyCommand;
import com.damso.admin.service.member.command.MemberRegisterCommand;

public interface MemberEditor {
    void register(MemberRegisterCommand command);

    void modify(Long memberId, MemberModifyCommand command);
}
