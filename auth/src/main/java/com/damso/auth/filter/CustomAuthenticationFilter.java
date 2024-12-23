package com.damso.auth.filter;

import com.damso.auth.service.JwtTokenProvider;
import com.damso.core.constant.AuthTokenStatus;
import com.damso.domain.cache.repository.auth.CacheAuthTokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
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
            String accessToken = jwtTokenProvider.extractToken(request);
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

        if (status.equals(AuthTokenStatus.EXPIRED)) {
            response.addHeader("refresh-Token", "true");
        } else if (status.equals(AuthTokenStatus.TAMPERED) || status.equals(AuthTokenStatus.EMPTY)) {
            response.addHeader("Clear-Token", "true");
        }

        try (PrintWriter writer = response.getWriter()) {
            writer.print(objectMapper.writeValueAsString(result));
            writer.flush();
        } catch (IOException e) {
            log.error("error: {}", e.getMessage());
        }
    }
}
