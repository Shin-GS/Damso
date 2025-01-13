package com.damso.user.controller.auth;

import com.damso.core.request.regex.pattern.CommonRegexPattern;
import com.damso.core.request.regex.pattern.MemberRegexPattern;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
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
