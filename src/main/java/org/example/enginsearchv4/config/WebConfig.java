package org.example.enginsearchv4.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/thumbnails/**")
                .addResourceLocations("file:/home/minaoui/Documents/web/");
        registry.addResourceHandler("/downloads/**")
                .addResourceLocations("file:/home/minaoui/Documents/web/");
    }
}
