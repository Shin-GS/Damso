package com.damso.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Profile("local")
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${file.access.path}")
    private String accessPath;

    @Value("${file.upload.path}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(accessPath + "/**")
                .addResourceLocations("file:" + uploadPath + "/");
    }
}

