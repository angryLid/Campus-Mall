package io.spring.guides.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"io.spring.guides.mbg.dao"
        , "io.spring.guides.mapper"})
public class MyBatisConfig {
}
