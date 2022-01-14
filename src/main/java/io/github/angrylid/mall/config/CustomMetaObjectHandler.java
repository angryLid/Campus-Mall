package io.github.angrylid.mall.config;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void insertFill(MetaObject metaObject) {
        logger.info("start insert fill");
        strictInsertFill(metaObject, "createdAt", LocalDateTime.class, LocalDateTime.now());
        strictInsertFill(metaObject, "modifiedAt", LocalDateTime.class, LocalDateTime.now());

        logger.info("finish insert fill");
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        logger.info("start update fill");
        strictUpdateFill(metaObject, "modifiedAt", LocalDateTime.class, LocalDateTime.now());

    }

}
