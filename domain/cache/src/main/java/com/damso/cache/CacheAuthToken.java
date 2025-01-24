package com.damso.cache;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@RedisHash(value = "auth", timeToLive = 2419200)
public class CacheAuthToken implements Serializable {
    @Id
    private Long memberId;

    @Indexed
    private String accessToken;

    @Indexed
    private String refreshToken;

    private LocalDateTime lastRefreshTime;

    public static CacheAuthToken of(Long memberId,
                                    String accessToken,
                                    String refreshToken,
                                    LocalDateTime refreshTime) {
        CacheAuthToken cacheAuthToken = new CacheAuthToken();
        cacheAuthToken.memberId = memberId;
        cacheAuthToken.accessToken = accessToken;
        cacheAuthToken.refreshToken = refreshToken;
        cacheAuthToken.lastRefreshTime = refreshTime;
        return cacheAuthToken;
    }

    public CacheAuthToken refresh(String accessToken) {
        this.accessToken = accessToken;
        this.lastRefreshTime = LocalDateTime.now();
        return this;
    }
}
