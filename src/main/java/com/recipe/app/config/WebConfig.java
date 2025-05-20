package com.recipe.app.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.upload.dir:${user.home}/uploads/recipe-images}")
    private String uploadDir;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // Your frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map the static resources location to the upload directory
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        String uploadAbsolutePath = uploadPath.toString();

        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + uploadAbsolutePath + "/")
                .setCachePeriod(3600);
    }
}