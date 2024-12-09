package com.damso.user.api.auth;

import com.damso.core.response.success.SuccessCode;
import com.damso.core.response.success.SuccessResponse;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/oauth2")
public class SnsSignupController {
    @GetMapping("/success")
    public SuccessResponse oauth2Login(OAuth2AuthenticationToken authentication) {
        OAuth2User user = authentication.getPrincipal();
        String email = user.getAttribute("email");
        String name = user.getAttribute("name");
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }
}
