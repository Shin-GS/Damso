package com.damso.admin.controller.member;

import com.damso.admin.service.member.MemberFinder;
import com.damso.admin.service.member.request.FilterOptionRequest;
import com.damso.admin.service.member.request.MemberSearchFilterRequest;
import com.damso.admin.service.member.response.MemberSearchResponse;
import com.damso.core.enums.member.MemberStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberFinder memberFinder;

    @GetMapping("/members")
    public String memberList(MemberSearchFilterRequest request,
                             @PageableDefault(size = 1) Pageable pageable,
                             Model model) {
        Page<MemberSearchResponse> members = memberFinder.findMembers(request, pageable);
        model.addAttribute("filter", request);
        model.addAttribute("statusOptions", getStatusOptions());
        model.addAttribute("members", members.getContent());
        model.addAttribute("page", members);
        return "views/member/memberList";
    }

    private List<FilterOptionRequest> getStatusOptions() {
        return Arrays.stream(MemberStatusType.values())
                .map(FilterOptionRequest::of)
                .toList();
    }
}
