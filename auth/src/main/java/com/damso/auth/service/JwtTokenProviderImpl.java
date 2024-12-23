package com.damso.auth.service;

import com.damso.auth.service.model.MemberAuthModel;
import com.damso.auth.session.SessionMember;
import com.damso.core.constant.AuthTokenStatus;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    public AuthTokenStatus validate(String token) {
        try {
            if (!StringUtils.hasText(token)) {
                return AuthTokenStatus.EMPTY;
            }

            Jwts.parser()
                    .verifyWith(jwtSigningKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return AuthTokenStatus.NORMAL;

        } catch (ExpiredJwtException exception) {
            log.error("Token Expired");
            return AuthTokenStatus.EXPIRED;

        } catch (JwtException exception) {
            log.error("Token Tampered");
            return AuthTokenStatus.TAMPERED;

        } catch (NullPointerException exception) {
            log.error("Token is null");
            return AuthTokenStatus.EMPTY;
        }
    }

    @Override
    public Authentication getAuthentication(String token) {
        try {
            Long memberId = Long.valueOf(getMemberId(token));
            return memberRepository.findById(memberId)
                    .map(member -> new UsernamePasswordAuthenticationToken(
                            new SessionMember(member),
                            null,
                            List.of(new SimpleGrantedAuthority(SessionMember.ROLE_PREFIX + member.getRole().name())))
                    )
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
    public MemberAuthModel generateAccessToken(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        if (!member.isActive()) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_ACTIVE);
        }

        CacheAuthToken cacheAuthToken = cacheAuthTokenRepository.findById(memberId)
                .orElse(CacheAuthToken.of(
                        member.getId(),
                        generateToken(member, validityInMilliseconds),
                        generateToken(member, validityRefreshInMilliseconds),
                        LocalDateTime.now().plusWeeks(2)));
        cacheAuthTokenRepository.save(cacheAuthToken);
        return MemberAuthModel.of(cacheAuthToken);
    }

    @Override
    public MemberAuthModel refreshToken(String refreshToken) {
        CacheAuthToken cacheAuthToken = cacheAuthTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new BusinessException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
        Member member = memberRepository.findById(cacheAuthToken.getMemberId())
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        cacheAuthToken.refresh(generateToken(member, validityInMilliseconds));
        cacheAuthTokenRepository.save(cacheAuthToken);
        return MemberAuthModel.of(cacheAuthToken);
    }

    private String generateToken(Member member, long validityInMilliseconds) {
        Date now = new Date();
        return Jwts.builder()
                .claims(createClaims(member))
                .subject(String.valueOf(member.getId()))
                .signWith(jwtSigningKey)
                .issuedAt(now)
                .expiration(new Date(System.currentTimeMillis() + validityInMilliseconds))
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
