package com.damso.admin.controller.view;

import com.damso.admin.service.member.command.MemberSearchCommand;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class MemberController {
    @GetMapping("/members")
    public String memberList(Model model) {
        model.addAttribute("searchCommand", MemberSearchCommand.ofEmpty());
        model.addAttribute("members", new ArrayList<>());
        return "fragments/member/memberList";
    }
}
