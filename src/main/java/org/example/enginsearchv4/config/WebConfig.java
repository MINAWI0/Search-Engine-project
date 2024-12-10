package org.example.enginsearchv4.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.storage.thumbnail-path}")
    private String thumbnailPath;

    @Value("${app.storage.download-path}")
    private String downloadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/thumbnails/**")
                .addResourceLocations("file:" + thumbnailPath);
        registry.addResourceHandler("/downloads/**")
                .addResourceLocations("file:" + downloadPath);
    }
}