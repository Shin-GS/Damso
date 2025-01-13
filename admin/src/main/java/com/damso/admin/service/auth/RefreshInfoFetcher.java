package com.damso.admin.service.auth;

import com.damso.admin.service.auth.response.RefreshInfoResponse;

public interface RefreshInfoFetcher {
    RefreshInfoResponse refresh(Long memberId);
}
