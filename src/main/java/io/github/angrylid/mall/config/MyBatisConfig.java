package io.github.angrylid.mall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"io.github.angrylid.mall.mbg.dao"
        , "io.github.angrylid.mall.mapper",
        "io.github.angrylid.mall.generated.mapper"})
public class MyBatisConfig {
}
