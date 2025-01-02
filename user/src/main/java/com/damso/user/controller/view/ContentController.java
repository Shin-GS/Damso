package com.damso.user.controller.view;

import com.damso.user.service.content.model.ContentPageEditorModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ContentController {
    @GetMapping("/contents/edit/{contentId}")
    public String contentEdit(@PathVariable("contentId") Long contentId,
                              Model model) {

        // todo 콘텐츠를 조회해서 page에 맞도록 수정 필요(pageable이 아니고 content-page에 맞도록 신규 구조가 필요함)
        // ex)/hx/contents/{contentId}/{id}를 구현하기 위함

        model.addAttribute("contentId", contentId);

        List<ContentPageEditorModel> models = new ArrayList<>();
        models.add(ContentPageEditorModel.of(1L, "text"));
        models.add(ContentPageEditorModel.of(2L, "image"));
        models.add(ContentPageEditorModel.of(3L, "video"));
        Page<ContentPageEditorModel> pageableContent = new PageImpl<>(models, Pageable.ofSize(3), 3);
        model.addAttribute("pages", pageableContent);

        model.addAttribute("isPublished", false);
        model.addAttribute("currentPageId", 1L);
        return "fragments/content/contentEdit";
    }
}
