package com.damso.core.response.hx.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FragmentResponseBuilder {
    private final List<FragmentResponse> fragments = new ArrayList<>();
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .findAndRegisterModules()
            .configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public FragmentResponseBuilder addFragment(String templatePath,
                                               String fragmentId,
                                               Map<String, Object> model) {
        this.fragments.add(FragmentResponse.builder()
                .templatePath(templatePath)
                .fragmentId(fragmentId)
                .model(model)
                .build());
        return this;
    }

    public FragmentResponseBuilder addFragment(String templatePath,
                                               String fragmentId,
                                               String key,
                                               Object value) {
        this.fragments.add(FragmentResponse.builder()
                .templatePath(templatePath)
                .fragmentId(fragmentId)
                .model(Map.of(key, value))
                .build());
        return this;
    }

    public FragmentResponseBuilder addTemplate(String templatePath,
                                               Map<String, Object> model) {
        this.fragments.add(FragmentResponse.builder()
                .templatePath(templatePath)
                .model(model)
                .build());
        return this;
    }

    public FragmentResponseBuilder addHtml(String htmlContent) {
        this.fragments.add(FragmentResponse.builder()
                .model(Map.of("html", htmlContent))
                .build());
        return this;
    }

    public List<FragmentResponse> build() {
        return fragments;
    }

    public String toJson() {
        try {
            return objectMapper.writeValueAsString(fragments);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert fragments to JSON", e);
        }
    }

    public static List<FragmentResponse> parseFromString(String json) {
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, FragmentResponse.class));

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON into FragmentResponse objects", e);
        }
    }
}
