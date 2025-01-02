package com.damso.admin.service.auth;

import com.damso.admin.service.auth.model.RefreshInfoModel;

public interface RefreshInfoFetcher {
    RefreshInfoModel refresh(Long memberId);
}
