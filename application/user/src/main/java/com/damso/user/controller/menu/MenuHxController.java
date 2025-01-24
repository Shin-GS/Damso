package com.damso.user.controller.menu;

import com.damso.common.auth.session.SessionMemberId;
import com.damso.common.request.ModelAndViewBuilder;
import com.damso.userservice.auth.RefreshInfoFetcher;
import com.damso.userservice.auth.response.RefreshInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/hx")
@RequiredArgsConstructor
public class MenuHxController {
    private final RefreshInfoFetcher refreshInfoFetcher;

    @GetMapping("/menu")
    public List<ModelAndView> menu(@SessionMemberId Long memberId) {
        if (ObjectUtils.isEmpty(memberId)) {
            return new ModelAndViewBuilder()
                    .addFragment("templates/components/menu.html", "components/menu :: guest-menu")
                    .build();
        }

        RefreshInfoResponse refreshInfoResponse = refreshInfoFetcher.refresh(memberId);
        Map<String, Object> menuData = new HashMap<>();
        menuData.put("info", refreshInfoResponse);
        return new ModelAndViewBuilder()
                .addFragment("templates/components/menu.html", "components/menu :: user-menu", menuData)
                .build();
    }
}
