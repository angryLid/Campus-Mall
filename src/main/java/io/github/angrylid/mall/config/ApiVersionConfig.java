package io.github.angrylid.mall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.github.angrylid.mall.api.client.ClientVersion;

@Configuration
public class ApiVersionConfig implements WebMvcConfigurer {
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/client", c -> c.isAnnotationPresent(ClientVersion.class));
    }
}
