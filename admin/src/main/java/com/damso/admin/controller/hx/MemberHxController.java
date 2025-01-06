package com.damso.admin.controller.hx;

import com.damso.admin.service.member.MemberFinder;
import com.damso.admin.service.member.command.MemberSearchFilterCommand;
import com.damso.admin.service.member.model.MemberSearchModel;
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
    public String memberList(MemberSearchFilterCommand command,
                             @PageableDefault(size = 1) Pageable pageable,
                             Model model) {
        Page<MemberSearchModel> members = memberFinder.findMembers(command, pageable);
        model.addAttribute("members", members.getContent());
        model.addAttribute("page", members);
        return "components/member/memberListContainer :: memberListContainer";
    }
}
