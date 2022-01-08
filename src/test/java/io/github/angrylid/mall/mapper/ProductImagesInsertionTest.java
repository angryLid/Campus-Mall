package io.github.angrylid.mall.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.angrylid.mall.generated.entity.ProductImage;
import io.github.angrylid.mall.generated.mapper.ProductImageMapper;

@SpringBootTest
public class ProductImagesInsertionTest {
    @Autowired
    ProductImageMapper productImageMapper;

    @Test
    void insertImages() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        var content = classLoader.getResourceAsStream("imageEncrypted.txt");
        String text = new String(content.readAllBytes(), StandardCharsets.UTF_8);

        var entity = new ProductImage();
        entity.setContent(text);
        entity.setProductId(1);
        entity.setId(1);

        productImageMapper.updateById(entity);

        assertThat(entity.getId()).isNotNull();

    }
}
