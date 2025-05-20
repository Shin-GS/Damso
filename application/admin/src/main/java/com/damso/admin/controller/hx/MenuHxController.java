package com.damso.admin.controller.hx;

import com.damso.adminservice.auth.RefreshInfoFetcher;
import com.damso.adminservice.auth.response.RefreshInfoResponse;
import com.damso.common.auth.session.SessionMemberId;
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
    public String menu(Model model,
                       @SessionMemberId Long memberId) {
        if (ObjectUtils.isEmpty(memberId)) {
            String fragment = " :: guest-menu";
            return "components/menu" + fragment;
        }

        RefreshInfoResponse refreshInfo = refreshInfoFetcher.refresh(memberId);
        model.addAttribute("username", refreshInfo.name());

        String fragment = " :: admin-menu";
        return "components/menu" + fragment;
    }
}
