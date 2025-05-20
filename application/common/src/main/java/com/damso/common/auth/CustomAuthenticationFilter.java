package com.damso.common.auth;

import com.damso.cache.repository.CacheAuthTokenRepository;
import com.damso.core.enums.auth.AuthTokenStatus;
import com.damso.core.enums.auth.AuthTokenType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final CacheAuthTokenRepository cacheAuthTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String authToken = jwtTokenProvider.extractCookie(request, AuthTokenType.AUTH);
            if (authToken == null) {
                filterChain.doFilter(request, response);
                return;
            }

            AuthTokenStatus tokenStatus = jwtTokenProvider.validate(authToken);
            if (tokenStatus.equals(AuthTokenStatus.NORMAL)) {
                authenticateToken(authToken, response);
                filterChain.doFilter(request, response);
                return;
            }

            // refresh token
            String refreshToken = jwtTokenProvider.extractCookie(request, AuthTokenType.REFRESH);
            if (refreshToken == null) {
                jwtTokenProvider.deleteJwtCookie(response);
            } else {
                jwtTokenProvider.refreshJwtCookie(response, refreshToken);
            }

            filterChain.doFilter(request, response);

        } catch (RuntimeException exception) {
            jwtTokenProvider.deleteJwtCookie(response);
        }
    }

    private void authenticateToken(String accessToken, HttpServletResponse response) {
        cacheAuthTokenRepository.findByAccessToken(accessToken)
                .ifPresentOrElse(
                        cachedToken -> {
                            Authentication authentication = jwtTokenProvider.getAuthentication(cachedToken.getAccessToken());
                            if (authentication != null) {
                                SecurityContextHolder.getContext().setAuthentication(authentication);
                            }

                            // token exists in cache, but user data not exists in DB
                            else {
                                jwtTokenProvider.deleteJwtCookie(response);
                            }
                        },
                        () -> jwtTokenProvider.deleteJwtCookie(response)
                );
    }
}
