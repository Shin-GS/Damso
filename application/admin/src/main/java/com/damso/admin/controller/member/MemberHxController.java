package com.damso.admin.controller.member;

import com.damso.adminservice.member.MemberFinder;
import com.damso.adminservice.member.request.MemberSearchFilterRequest;
import com.damso.adminservice.member.response.MemberSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hx")
@RequiredArgsConstructor
public class MemberHxController {
    private final MemberFinder memberFinder;

    @GetMapping("/members")
    public String memberList(MemberSearchFilterRequest request,
                             @PageableDefault(size = 1) Pageable pageable,
                             Model model) {
        Page<MemberSearchResponse> members = memberFinder.findMembers(request, pageable);
        model.addAttribute("members", members.getContent());
        model.addAttribute("page", members);
        String fragment = " :: memberListContainer";
        return "components/member/memberListContainer" + fragment;
    }
}
