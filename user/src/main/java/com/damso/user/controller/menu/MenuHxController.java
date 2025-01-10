package com.damso.user.controller.menu;

import com.damso.auth.session.SessionMemberId;
import com.damso.core.enums.member.MemberRoleType;
import com.damso.user.service.auth.RefreshInfoFetcher;
import com.damso.user.service.auth.model.RefreshInfoModel;
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
            model.addAttribute("role", MemberRoleType.GUEST.getCode());
            return "components/menu";
        }

        RefreshInfoModel infoModel = refreshInfoFetcher.refresh(memberId);
        model.addAttribute("role", infoModel.role().getCode());
        model.addAttribute("info", infoModel);
        return "components/menu";
    }
}
