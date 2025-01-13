package com.damso.admin.service.member;

import com.damso.admin.service.member.request.MemberModifyRequest;
import com.damso.admin.service.member.request.MemberRegisterRequest;

public interface MemberEditor {
    void register(MemberRegisterRequest request);

    void modify(Long memberId, MemberModifyRequest request);
}
