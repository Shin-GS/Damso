package com.damso.user.filter;

import com.damso.core.response.error.ErrorCode;
import com.damso.core.response.exception.BusinessException;
import com.damso.domain.cache.entity.auth.CacheAuthToken;
import com.damso.domain.cache.repository.auth.CacheAuthTokenRepository;
import com.damso.domain.db.entity.member.Member;
import com.damso.domain.db.repository.member.MemberRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtTokenProviderImpl implements JwtTokenProvider {
    private final SecretKey jwtSigningKey;
    private final long validityInMilliseconds;
    private final long validityRefreshInMilliseconds;
    private final MemberRepository memberRepository;
    private final CacheAuthTokenRepository cacheAuthTokenRepository;

    public JwtTokenProviderImpl(MemberRepository memberRepository,
                                CacheAuthTokenRepository cacheAuthTokenRepository) {
        this.jwtSigningKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode("kIq8c8q1MRjCxFg9FUEt+71FsDLF1xkvflGSw8UOYzA="));
        this.validityInMilliseconds = 86400000L;
        this.validityRefreshInMilliseconds = 2419200000L;
        this.memberRepository = memberRepository;
        this.cacheAuthTokenRepository = cacheAuthTokenRepository;
    }

    @Override
    public boolean validate(String token) {
        try {
            if (!StringUtils.hasText(token)) {
                return false;
            }

            Jwts.parser()
                    .verifyWith(jwtSigningKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;

        } catch (ExpiredJwtException exception) {
            log.error("Token Expired");

        } catch (JwtException exception) {
            log.error("Token Tampered");

        } catch (NullPointerException exception) {
            log.error("Token is null");
        }

        return false;
    }

    @Override
    public Authentication getAuthentication(String token) {
        try {
            Long memberId = Long.valueOf(getMemberId(token));
            return memberRepository.findById(memberId)
                    .map(member -> new UsernamePasswordAuthenticationToken(member, null))
                    .orElse(null);
        } catch (Exception e) {
            log.error("Failed to get authentication from token", e);
            return null;
        }
    }

    private String getMemberId(String token) {
        return Jwts.parser()
                .verifyWith(jwtSigningKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("memberId")
                .toString();
    }

    @Override
    public String generateAccessToken(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        CacheAuthToken cacheAuthToken = cacheAuthTokenRepository.save(CacheAuthToken.of(member.getId(),
                generateToken(member, validityInMilliseconds),
                generateToken(member, validityRefreshInMilliseconds),
                LocalDateTime.now().plusWeeks(2)));
        return cacheAuthToken.getAccessToken();
    }

    private String generateToken(Member member, long validityInMilliseconds) {
        Date now = new Date();
        return Jwts.builder()
                .claims(createClaims(member))
                .subject(String.valueOf(member.getId()))
                .signWith(jwtSigningKey)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + validityInMilliseconds))
                .compact();
    }

    private static Map<String, Object> createClaims(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", member.getId());
        claims.put("memberName", member.getName());
        claims.put("memberRole", member.getRole());
        return claims;
    }
}
