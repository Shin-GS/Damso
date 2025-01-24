package com.damso.userservice.auth;

import com.damso.userservice.auth.response.RefreshInfoResponse;

public interface RefreshInfoFetcher {
    RefreshInfoResponse refresh(Long memberId);
}
