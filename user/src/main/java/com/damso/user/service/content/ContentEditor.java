package com.damso.user.service.content;

import com.damso.user.service.content.model.CreateContentModel;

public interface ContentEditor {
    CreateContentModel create(Long memberId);
}
