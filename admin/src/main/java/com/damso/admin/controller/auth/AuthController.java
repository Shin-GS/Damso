package com.damso.admin.controller.auth;

import com.damso.core.request.regex.pattern.MemberRegexPattern;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("memberRegexPattern", MemberRegexPattern.getMap());
        return "views/auth/login";
    }
}
