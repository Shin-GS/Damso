package com.damso.user.service.auth.model;

import com.damso.domain.cache.entity.auth.CacheAuthToken;

public record MemberAuthModel(String auth,
                              String refresh) {
    public static MemberAuthModel of(CacheAuthToken token) {
        return new MemberAuthModel(token.getAccessToken(), token.getRefreshToken());
    }
}
