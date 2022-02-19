package io.github.angrylid.mall.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.github.angrylid.mall.jwt.AdminAuthIntercepter;
import io.github.angrylid.mall.jwt.ClientAuthInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private ClientAuthInterceptor clientAuthInterceptor;

    @Autowired
    private AdminAuthIntercepter adminAuthIntercepter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(clientAuthInterceptor)
                .addPathPatterns("/client/**");

        registry.addInterceptor(adminAuthIntercepter)
                .addPathPatterns("/admin/**");
    }
}
