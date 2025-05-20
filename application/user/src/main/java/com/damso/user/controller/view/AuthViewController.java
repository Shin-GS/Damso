package com.damso.user.controller.view;

import com.damso.common.request.pattern.CommonRegexPattern;
import com.damso.common.request.pattern.MemberRegexPattern;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthViewController {
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("memberRegexPattern", CommonRegexPattern.getMap(MemberRegexPattern.class));
        return "views/auth/login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("memberRegexPattern", CommonRegexPattern.getMap(MemberRegexPattern.class));
        return "views/auth/signup";
    }
}
