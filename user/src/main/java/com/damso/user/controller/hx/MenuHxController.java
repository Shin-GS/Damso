package com.damso.user.controller.hx;

import com.damso.auth.session.SessionMemberId;
import com.damso.user.service.auth.RefreshInfoFetcher;
import com.damso.user.service.auth.model.RefreshInfoModel;
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
        return "components/menu :: user-menu";
    }
}
