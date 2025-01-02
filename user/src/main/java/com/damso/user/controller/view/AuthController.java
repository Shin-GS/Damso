package com.damso.user.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/login")
    public String login() {
        return "fragments/auth/login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "fragments/auth/signup";
    }
}
