package com.damso.user.service.auth;

import com.damso.user.service.auth.model.RefreshInfoModel;

public interface RefreshInfoFetcher {
    RefreshInfoModel refresh(Long memberId);
}
