package com.damso.user.service.member.auth;

import com.damso.domain.db.entity.member.Member;
import com.damso.domain.db.repository.member.MemberRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtTokenProviderImpl implements JwtTokenProvider {
    private final SecretKey JWT_SIGNING_KEY;
    private final long validityInMilliseconds;
    private final MemberRepository memberRepository;

    public JwtTokenProviderImpl(MemberRepository memberRepository) {
        this.JWT_SIGNING_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode("kIq8c8q1MRjCxFg9FUEt+71FsDLF1xkvflGSw8UOYzA="));
        this.validityInMilliseconds = 3600000;
        this.memberRepository = memberRepository;
    }

    @Override
    public String extractToken(HttpServletRequest request) {
        String authorizationToken = request.getHeader("Authorization");
        return StringUtils.hasText(authorizationToken) && authorizationToken.startsWith("Bearer ")
                ? authorizationToken.substring(7)
                : null;
    }

    @Override
    public boolean validate(String token) {
        try {
            if (!StringUtils.hasText(token)) {
                return false;
            }

            Jwts.parser()
                    .verifyWith(JWT_SIGNING_KEY)
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
            String memberIdString = getMemberId(token);
            Long memberId = Long.valueOf(memberIdString);

            return memberRepository.findById(memberId)
                    .map(member -> new UsernamePasswordAuthenticationToken(member, member.getPassword()))
                    .orElse(null);
        } catch (Exception e) {
            log.error("Failed to get authentication from token", e);
            return null;
        }
    }

    private String getMemberId(String token) {
        return Jwts.parser()
                .verifyWith(JWT_SIGNING_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("memberId")
                .toString();
    }

    @Override
    public String generateToken(Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();
        Date now = new Date();
        return Jwts.builder()
                .claims(createClaims(member))
                .subject(String.valueOf(member.getId()))
                .signWith(JWT_SIGNING_KEY)
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
