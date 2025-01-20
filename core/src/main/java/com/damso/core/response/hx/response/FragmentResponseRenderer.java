package com.damso.core.response.hx.response;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FragmentResponseRenderer {
    private final TemplateEngine templateEngine;

    public String renderFragment(FragmentResponse fragmentResponse) {
        // HTML 리턴
        if (ObjectUtils.isEmpty(fragmentResponse.getTemplatePath())) {
            return Optional.ofNullable(fragmentResponse.getModel().get("html"))
                    .map(Object::toString)
                    .orElse("");
        }

        // 템플릿 사용
        Context context = new Context();
        Optional.ofNullable(fragmentResponse.getModel())
                .ifPresent(context::setVariables);

        return templateEngine.process(fragmentResponse.getTemplateWithFragmentPath(), context);
    }
}
