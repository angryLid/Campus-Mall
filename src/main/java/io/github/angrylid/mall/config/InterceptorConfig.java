package io.github.angrylid.mall.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.github.angrylid.mall.jwt.AdminAuthIntercepter;
import io.github.angrylid.mall.jwt.ClientAuthInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private ClientAuthInterceptor clientAuthInterceptor;
    private AdminAuthIntercepter adminAuthIntercepter;

    @Autowired
    public InterceptorConfig(ClientAuthInterceptor clientAuthInterceptor, AdminAuthIntercepter adminAuthIntercepter) {
        this.clientAuthInterceptor = clientAuthInterceptor;
        this.adminAuthIntercepter = adminAuthIntercepter;
    }

    /**
     * 注册拦截器
     * 登录注册不拦其他都拦
     * 
     * @param registry
     * 
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(clientAuthInterceptor)
                .addPathPatterns("/client/**").excludePathPatterns("/client/auth/**");

        registry.addInterceptor(adminAuthIntercepter)
                .addPathPatterns("/admin/**").excludePathPatterns("/admin/auth/**");
    }
}
