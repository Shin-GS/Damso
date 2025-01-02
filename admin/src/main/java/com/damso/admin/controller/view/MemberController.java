package com.damso.admin.controller.view;

import com.damso.admin.service.member.MemberFinder;
import com.damso.admin.service.member.command.FilterOptionCommand;
import com.damso.admin.service.member.command.MemberSearchFilterCommand;
import com.damso.admin.service.member.model.MemberSearchModel;
import com.damso.core.constant.MemberStatusType;
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
    public String memberList(MemberSearchFilterCommand command,
                             @PageableDefault(size = 1) Pageable pageable,
                             Model model) {
        Page<MemberSearchModel> members = memberFinder.findMembers(command, pageable);
        model.addAttribute("filter", command);
        model.addAttribute("statusOptions", getStatusOptions());
        model.addAttribute("members", members.getContent());
        model.addAttribute("page", members);
        return "fragments/member/memberList";
    }

    private List<FilterOptionCommand> getStatusOptions() {
        return Arrays.stream(MemberStatusType.values())
                .map(FilterOptionCommand::of)
                .toList();
    }
}
