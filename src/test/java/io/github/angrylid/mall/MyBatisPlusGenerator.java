package io.github.angrylid.mall;

import java.util.Collections;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.fill.Column;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest
public class MyBatisPlusGenerator {

        @Autowired
        private Environment env;

        @Test
        void run() {
                var projectPath = System.getProperty("user.dir");
                var url = env.getProperty("spring.datasource.url");
                var username = env.getProperty("spring.datasource.username");
                var password = env.getProperty("spring.datasource.password");
                FastAutoGenerator.create(url, username, password)
                                .globalConfig(builder -> {
                                        builder.author("angrylid") // 设置作者
                                                        // 开启 swagger 模式
                                                        .fileOverride() // 覆盖已生成文件
                                                        .outputDir(projectPath + "/src/main/java").disableOpenDir(); // 指定输出目录
                                })
                                .packageConfig(builder -> {
                                        builder.parent("io.github.angrylid.mall") // 设置父包名
                                                        .moduleName("generated")// 设置父包模块名
                                                        .entity("entity")
                                                        .mapper("mapper")
                                                        // .service("service")
                                                        // .serviceImpl("service.impl")
                                                        // .controller("controller")
                                                        .pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                                                                        projectPath + "/src/main/resources/io/github/angrylid/mall/generated/mapper/")); // 设置mapperXml生成路径
                                })
                                .strategyConfig(builder -> {
                                        builder.addInclude("user")
                                                        .addInclude("product")// 设置需要生成的表名
                                                        .addInclude("product_image")
                                                        .addInclude("relation")
                                                        .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                                }).strategyConfig(builder -> {
                                        builder.entityBuilder().logicDeleteColumnName("is_deleted")
                                                        .addTableFills(new Column("created_at", FieldFill.INSERT))
                                                        .addTableFills(new Column("modified_at",
                                                                        FieldFill.INSERT_UPDATE))
                                                        .build();
                                })
                                .templateConfig(builder -> builder
                                                .disable(TemplateType.SERVICE, TemplateType.SERVICEIMPL,
                                                                TemplateType.CONTROLLER, TemplateType.XML)
                                                .build())
                                // .templateEngine(new FreemarkerTemplateEngine())
                                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                                .execute();
        }

        @Test
        void checkProperty() {
                Assertions.assertEquals("root", env.getProperty("spring.datasource.username"));
        }
}
