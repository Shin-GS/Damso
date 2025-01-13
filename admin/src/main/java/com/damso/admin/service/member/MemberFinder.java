package com.damso.admin.service.member;

import com.damso.admin.service.member.request.MemberSearchFilterRequest;
import com.damso.admin.service.member.response.MemberInfoResponse;
import com.damso.admin.service.member.response.MemberSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberFinder {
    Page<MemberSearchResponse> findMembers(MemberSearchFilterRequest request,
                                           Pageable pageable);

    MemberInfoResponse get(Long memberId);
}
