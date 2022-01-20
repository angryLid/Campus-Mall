package io.github.angrylid.mall.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({ "io.github.angrylid.mall.mbg.dao", "io.github.angrylid.mall.mapper",
                "io.github.angrylid.mall.generated.mapper" })
public class MyBatisConfig {
        @Bean
        public MybatisPlusInterceptor mybatisPlusInterceptor() {
                MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
                interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
                return interceptor;
        }
}
