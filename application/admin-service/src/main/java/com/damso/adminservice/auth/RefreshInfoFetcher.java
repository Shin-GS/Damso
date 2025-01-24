package com.damso.adminservice.auth;

import com.damso.adminservice.auth.response.RefreshInfoResponse;

public interface RefreshInfoFetcher {
    RefreshInfoResponse refresh(Long memberId);
}
