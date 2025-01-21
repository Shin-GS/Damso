package com.damso.core.response;

import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ModelAndViewBuilder {
    private final List<ModelAndView> fragments = new ArrayList<>();

    public ModelAndViewBuilder addFragment(String developOnlyPath,
                                           String viewName,
                                           Map<String, Object> model) {
        this.fragments.add(new ModelAndView(viewName, model));
        return this;
    }

    public ModelAndViewBuilder addFragment(String developOnlyPath,
                                           String viewName,
                                           String key,
                                           String value) {
        this.fragments.add(new ModelAndView(viewName, Map.of(key, value)));
        return this;
    }

    public List<ModelAndView> build() {
        return fragments;
    }
}
