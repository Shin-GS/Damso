package com.demo.admin.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {
    @GetMapping("/members")
    public String memberList() {
        return "member/memberList";
    }
}
