package com.demo.admin.api.member;

import com.demo.admin.core.response.success.SuccessCode;
import com.demo.admin.core.response.success.SuccessResponse;
import com.demo.admin.service.member.MemberFinder;
import com.demo.admin.service.member.command.MemberSearchCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApi {
    private final MemberFinder memberFinder;

    @GetMapping
    public SuccessResponse search(MemberSearchCommand command,
                                  @PageableDefault(size = 1) Pageable pageable) {
        return SuccessResponse.of(SuccessCode.SUCCESS,
                memberFinder.findMembers(command, pageable));
    }
}
