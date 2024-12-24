package com.damso.admin.api.member;

import com.damso.admin.service.member.MemberEditor;
import com.damso.admin.service.member.MemberFinder;
import com.damso.admin.service.member.command.MemberModifyCommand;
import com.damso.admin.service.member.command.MemberRegisterCommand;
import com.damso.admin.service.member.command.MemberSearchCommand;
import com.damso.core.response.success.SuccessCode;
import com.damso.core.response.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberListApi {
    private final MemberFinder memberFinder;
    private final MemberEditor memberEditor;

    @GetMapping
    public SuccessResponse search(MemberSearchCommand command,
                                  @PageableDefault(size = 1) Pageable pageable) {
        return SuccessResponse.of(SuccessCode.SUCCESS,
                memberFinder.findMembers(command, pageable));
    }

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
