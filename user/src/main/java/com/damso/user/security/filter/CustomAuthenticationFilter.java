package com.damso.user.security.filter;

import com.damso.core.constant.AuthTokenStatus;
import com.damso.domain.cache.repository.auth.CacheAuthTokenRepository;
import com.damso.user.security.token.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;
    private final CacheAuthTokenRepository cacheAuthTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = extractAccessToken(request);
            if (accessToken == null) {
                filterChain.doFilter(request, response);
                return;
            }

            AuthTokenStatus tokenStatus = jwtTokenProvider.validate(accessToken);
            if (!tokenStatus.equals(AuthTokenStatus.NORMAL)) {
                setErrorResponse(HttpStatus.UNAUTHORIZED, response, "Invalid Token", tokenStatus);
                return;
            }

            authenticateToken(accessToken, response);
            filterChain.doFilter(request, response);

        } catch (UsernameNotFoundException exception) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, exception.getMessage(), AuthTokenStatus.EMPTY);
        } catch (RuntimeException exception) {
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, exception.getMessage(), AuthTokenStatus.EMPTY);
        }
    }

    private String extractAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    private void authenticateToken(String accessToken, HttpServletResponse response) {
        cacheAuthTokenRepository.findByAccessToken(accessToken)
                .ifPresentOrElse(
                        cachedToken -> {
                            Authentication authentication = jwtTokenProvider.getAuthentication(cachedToken.getAccessToken());
                            if (authentication != null) {
                                SecurityContextHolder.getContext().setAuthentication(authentication);
                            }

                            // redis 캐시에는 토큰이 있지만 DB에는 토큰이 삭제된 경우
                            else {
                                setErrorResponse(HttpStatus.UNAUTHORIZED, response, "Invalid Token", AuthTokenStatus.TAMPERED);
                            }
                        },
                        () -> setErrorResponse(HttpStatus.UNAUTHORIZED, response, "Token Not Found", AuthTokenStatus.EMPTY)
                );
    }

    private void setErrorResponse(HttpStatus httpStatus,
                                  HttpServletResponse response,
                                  String message,
                                  AuthTokenStatus status) {
        response.setStatus(httpStatus.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> result = new HashMap<>();
        result.put("message", message);

        switch (status) {
            case EXPIRED -> response.addHeader("refresh-Token", "true");
            case TAMPERED, EMPTY -> response.addHeader("Clear-Token", "true");
        }

        try (PrintWriter writer = response.getWriter()) {
            writer.print(objectMapper.writeValueAsString(result));
            writer.flush();
        } catch (IOException e) {
            log.error("error: {}", e.getMessage());
        }
    }
}
