package com.damso.user.service.member;

import com.damso.user.service.member.model.MemberInfoRefreshModel;

public interface MemberInfoFetcher {
    MemberInfoRefreshModel refresh(Long memberId);
}
