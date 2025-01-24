package com.damso.adminservice.member;

import com.damso.adminservice.member.request.MemberSearchFilterRequest;
import com.damso.adminservice.member.response.MemberInfoResponse;
import com.damso.adminservice.member.response.MemberSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberFinder {
    Page<MemberSearchResponse> findMembers(MemberSearchFilterRequest request,
                                           Pageable pageable);

    MemberInfoResponse get(Long memberId);
}
