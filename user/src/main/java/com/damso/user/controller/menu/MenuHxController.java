package com.damso.user.controller.menu;

import com.damso.auth.session.SessionMemberId;
import com.damso.user.service.auth.RefreshInfoFetcher;
import com.damso.user.service.auth.response.RefreshInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hx")
@RequiredArgsConstructor
public class MenuHxController {
    private final RefreshInfoFetcher refreshInfoFetcher;

    @GetMapping("/menu")
    public String menu(@SessionMemberId Long memberId,
                       Model model) {
        if (ObjectUtils.isEmpty(memberId)) {
            String fragment = " :: guest-menu";
            return "components/menu" + fragment;
        }

        RefreshInfoResponse refreshInfoResponse = refreshInfoFetcher.refresh(memberId);
        model.addAttribute("info", refreshInfoResponse);

        String fragment = " :: user-menu";
        return "components/menu" + fragment;
    }
}
