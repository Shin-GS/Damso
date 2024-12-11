package com.damso.domain.cache.repository.auth;

import com.damso.domain.cache.entity.auth.CacheAuthToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CacheAuthTokenRepository extends CrudRepository<CacheAuthToken, Long> {
    Optional<CacheAuthToken> findByAccessToken(String accessToken);

    Optional<CacheAuthToken> findByRefreshToken(String refreshToken);
}
