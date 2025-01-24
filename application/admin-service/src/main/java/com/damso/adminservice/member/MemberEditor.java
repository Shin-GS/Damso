package com.damso.adminservice.member;

import com.damso.adminservice.member.request.MemberModifyRequest;
import com.damso.adminservice.member.request.MemberRegisterRequest;

public interface MemberEditor {
    void register(MemberRegisterRequest request);

    void modify(Long memberId, MemberModifyRequest request);
}
