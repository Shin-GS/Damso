package com.damso.cache.repository;

import com.damso.cache.entity.CacheAuthToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CacheAuthTokenRepository extends CrudRepository<CacheAuthToken, Long> {
    Optional<CacheAuthToken> findByAccessToken(String accessToken);

    Optional<CacheAuthToken> findByRefreshToken(String refreshToken);
}
