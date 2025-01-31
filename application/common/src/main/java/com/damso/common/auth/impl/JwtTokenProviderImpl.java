package com.damso.common.auth.impl;

import com.damso.cache.entity.CacheAuthToken;
import com.damso.cache.repository.CacheAuthTokenRepository;
import com.damso.common.auth.JwtTokenProvider;
import com.damso.common.auth.session.SessionMember;
import com.damso.core.enums.auth.AuthTokenStatus;
import com.damso.core.enums.auth.AuthTokenType;
import com.damso.core.code.ErrorCode;
import com.damso.core.exception.BusinessException;
import com.damso.storage.entity.member.Member;
import com.damso.storage.repository.member.MemberRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Profile("!local")
@Component
@Transactional
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
    public String extractCookie(HttpServletRequest request,
                                AuthTokenType type) {
        if (request.getCookies() == null) {
            return null;
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(type.name()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
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
    public void generateJwtCookie(HttpServletResponse response,
                                  Long memberId) {
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
        CacheAuthToken savedCacheAuthToken = cacheAuthTokenRepository.save(cacheAuthToken);

        // cookie setting
        response.addCookie(createCookie(AuthTokenType.AUTH, savedCacheAuthToken.getAccessToken(), 2419200));
        response.addCookie(createCookie(AuthTokenType.REFRESH, savedCacheAuthToken.getRefreshToken(), 2419200));
    }

    @Override
    public void refreshJwtCookie(HttpServletResponse response,
                                 String refreshToken) {
        CacheAuthToken cacheAuthToken = cacheAuthTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new BusinessException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
        Member member = memberRepository.findById(cacheAuthToken.getMemberId())
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        cacheAuthToken.refresh(generateToken(member, validityInMilliseconds));
        CacheAuthToken savedCacheAuthToken = cacheAuthTokenRepository.save(cacheAuthToken);

        // cookie setting
        response.addCookie(createCookie(AuthTokenType.AUTH, savedCacheAuthToken.getAccessToken(), 2419200));
        response.addCookie(createCookie(AuthTokenType.REFRESH, savedCacheAuthToken.getRefreshToken(), 2419200));
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

    private Cookie createCookie(AuthTokenType type, String token, int maxAge) {
        Cookie cookie = new Cookie(type.name(), token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        cookie.setSecure(true);
        cookie.setAttribute("SameSite", "None");
        return cookie;
    }

    @Override
    public void deleteJwtCookie(HttpServletResponse response) {
        response.addCookie(createCookie(AuthTokenType.AUTH, "", 0));
        response.addCookie(createCookie(AuthTokenType.REFRESH, "", 0));
    }
}
