package com.damso.user.service.auth;

import com.damso.user.service.auth.response.RefreshInfoResponse;

public interface RefreshInfoFetcher {
    RefreshInfoResponse refresh(Long memberId);
}
