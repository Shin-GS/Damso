package com.damso.core.response.hx.response;

import lombok.*;
import org.springframework.util.ObjectUtils;

import java.util.Map;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FragmentResponse {
    private String templatePath;
    private String fragmentId;
    private Map<String, Object> model;

    public String getTemplateWithFragmentPath() {
        return ObjectUtils.isEmpty(fragmentId) ? templatePath : String.format("%s :: %s", templatePath, fragmentId);
    }

    @Override
    public String toString() {
        return "FragmentResponse{" +
                "templatePath='" + templatePath + '\'' +
                ", fragmentId='" + fragmentId + '\'' +
                ", model=" + model +
                '}';
    }
}
