package com.damso.admin.controller.member;

import com.damso.adminservice.member.MemberEditor;
import com.damso.adminservice.member.MemberFinder;
import com.damso.adminservice.member.request.MemberModifyRequest;
import com.damso.adminservice.member.request.MemberRegisterRequest;
import com.damso.core.code.SuccessCode;
import com.damso.common.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApi {
    private final MemberFinder memberFinder;
    private final MemberEditor memberEditor;

    @PostMapping
    public SuccessResponse register(@RequestBody MemberRegisterRequest request) {
        memberEditor.register(request);
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }

    @GetMapping("/{memberId}")
    public SuccessResponse get(@PathVariable("memberId") Long memberId) {
        return SuccessResponse.of(SuccessCode.SUCCESS, memberFinder.get(memberId));
    }

    @PutMapping("/{memberId}")
    public SuccessResponse modify(@PathVariable("memberId") Long memberId,
                                  @RequestBody MemberModifyRequest request) {
        memberEditor.modify(memberId, request);
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }
}
