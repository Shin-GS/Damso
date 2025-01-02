package com.damso.user.controller.hx;

import com.damso.auth.session.SessionMemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/hx")
@RequiredArgsConstructor
public class ContentEditHxController {
    @GetMapping("/contents/{contentId}/{pageId}")
    public String getPage(@PathVariable("contentId") Long contentId,
                          @PathVariable("pageId") Long pageId,
                          @SessionMemberId Long memberId,
                          Model model) {
        // 해당 콘텐츠의 적합한 수정자인지 여부 체크

        // 페이지 데이터 조회

        // 페이지 데이터에 맞는 콘텐츠 에디터 데이터 세팅

        List<String> files = new ArrayList<>();
        model.addAttribute("files", new PageImpl<>(files, Pageable.ofSize(3), 3));

        model.addAttribute("content", "");

        if (pageId == 1) {
            return "hx/contentEditor :: text-editor";
        } else if (pageId == 2) {
            return "hx/contentEditor :: image-editor";
        }

        return "hx/contentEditor :: video-editor";
    }

    @PostMapping("/contents/{contentId}")
    public String addPage(@PathVariable("contentId") Long contentId,
                          @SessionMemberId Long memberId,
                          Model model) {
        return "hx/contentEditor :: text-editor";
    }
}
