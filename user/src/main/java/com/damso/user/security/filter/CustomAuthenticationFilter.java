package com.damso.user.security.filter;

import com.damso.domain.cache.repository.auth.CacheAuthTokenRepository;
import com.damso.user.security.token.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String accessToken = authorizationHeader.substring(7);
            if (jwtTokenProvider.validate(accessToken)) {
                cacheAuthTokenRepository.findByAccessToken(accessToken)
                        .ifPresent(cacheAuthToken -> {
                            Authentication authentication = jwtTokenProvider.getAuthentication(cacheAuthToken.getAccessToken());
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        });
            } else {
                setErrorResponse(HttpStatus.UNAUTHORIZED, response, "Invalid Token", true);
                return;
            }

            filterChain.doFilter(request, response);

        } catch (UsernameNotFoundException exception) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, exception.getMessage(), true);
        } catch (RuntimeException exception) {
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, exception.getMessage(), false);
        }
    }

    private void setErrorResponse(HttpStatus httpStatus,
                                  HttpServletResponse response,
                                  String message,
                                  boolean isClearToken) throws IOException {
        response.setStatus(httpStatus.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> result = new HashMap<>();
        result.put("message", message);

        if (isClearToken) {
            response.addHeader("Clear-Token", "true");
        }

        PrintWriter writer = response.getWriter();
        writer.print(objectMapper.writeValueAsString(result));
        writer.flush();
    }
}
