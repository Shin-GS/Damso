package com.damso.user.api.member;

import com.damso.core.response.success.SuccessCode;
import com.damso.core.response.success.SuccessResponse;
import com.damso.user.security.model.SessionMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberApi {
    @GetMapping("/info")
    public SuccessResponse memberInfo(@AuthenticationPrincipal SessionMember sessionMember) {
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }
}
