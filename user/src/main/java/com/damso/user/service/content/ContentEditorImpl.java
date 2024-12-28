package com.damso.user.service.content;

import com.damso.user.service.content.model.CreateContentModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ContentEditorImpl implements ContentEditor {
    @Override
    public CreateContentModel create(Long memberId) {
        return CreateContentModel.of(memberId);
    }
}
