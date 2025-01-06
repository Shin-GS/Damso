package com.damso.admin.controller.menu;

import com.damso.admin.service.auth.RefreshInfoFetcher;
import com.damso.admin.service.auth.model.RefreshInfoModel;
import com.damso.auth.session.SessionMemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        if (memberId == null) {
            return "components/menu :: guest-menu";
        }

        RefreshInfoModel refreshInfo = refreshInfoFetcher.refresh(memberId);
        model.addAttribute("username", refreshInfo.name());
        return "components/menu :: admin-menu";
    }
}
