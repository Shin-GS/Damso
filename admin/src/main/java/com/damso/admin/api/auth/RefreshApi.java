package com.damso.admin.api.auth;

import com.damso.admin.service.auth.RefreshInfoFetcher;
import com.damso.auth.service.JwtTokenProvider;
import com.damso.auth.service.command.RefreshTokenCommand;
import com.damso.auth.session.SessionMemberId;
import com.damso.core.response.success.SuccessCode;
import com.damso.core.response.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RefreshApi {
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshInfoFetcher refreshInfoFetcher;

    @PutMapping("/refresh-token")
    public SuccessResponse refreshToken(@RequestBody RefreshTokenCommand command) {
        return SuccessResponse.of(SuccessCode.SUCCESS, jwtTokenProvider.refreshToken(command.refresh()));
    }

    @GetMapping("/refresh-info")
    public SuccessResponse refreshInfo(@SessionMemberId Long memberId) {
        return SuccessResponse.of(SuccessCode.SUCCESS, refreshInfoFetcher.refresh(memberId));
    }
}
