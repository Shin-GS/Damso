package com.damso.admin.controller.api.member;

import com.damso.admin.service.member.MemberEditor;
import com.damso.admin.service.member.MemberFinder;
import com.damso.admin.service.member.command.MemberModifyCommand;
import com.damso.admin.service.member.command.MemberRegisterCommand;
import com.damso.core.response.success.SuccessCode;
import com.damso.core.response.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApi {
    private final MemberFinder memberFinder;
    private final MemberEditor memberEditor;

    @PostMapping
    public SuccessResponse register(@RequestBody MemberRegisterCommand command) {
        memberEditor.register(command);
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }

    @GetMapping("/{memberId}")
    public SuccessResponse get(@PathVariable("memberId") Long memberId) {
        return SuccessResponse.of(SuccessCode.SUCCESS, memberFinder.get(memberId));
    }

    @PutMapping("/{memberId}")
    public SuccessResponse modify(@PathVariable("memberId") Long memberId,
                                  @RequestBody MemberModifyCommand command) {
        memberEditor.modify(memberId, command);
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }
}
