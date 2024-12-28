package com.damso.user.controller.api;

import com.damso.auth.session.SessionMemberId;
import com.damso.core.response.success.SuccessCode;
import com.damso.core.response.success.SuccessResponse;
import com.damso.user.service.content.ContentEditor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contents")
@RequiredArgsConstructor
public class ContentEditApi {
    private final ContentEditor contentEditor;

    @PostMapping
    public SuccessResponse createContent(@SessionMemberId Long memberId) {
        return SuccessResponse.of(SuccessCode.SUCCESS, contentEditor.create(memberId));
    }
}
