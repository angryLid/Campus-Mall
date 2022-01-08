package io.github.angrylid.mall.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.angrylid.mall.generated.entity.Product;
import io.github.angrylid.mall.generated.mapper.ProductMapper;

@SpringBootTest
public class ProductInsertionTest {
    @Autowired
    private ProductMapper productMapper;

    StringBuilder stringBuilder;

    String title;

    String description;

    @BeforeEach
    void testStringBuilder() {
        stringBuilder = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            stringBuilder.append("1234567890");
        }

        title = stringBuilder.toString();

        for (int i = 0; i < 45; i++) {
            stringBuilder.append("!@#$%^&*()");
        }

        description = stringBuilder.toString();
        // assertThat(title.length()).isEqualTo(500);

    }

    @Test
    void testInsert() {
        var product = new Product();

        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(new BigDecimal(12.123456));

        productMapper.insert(product);
        assertThat(product.getId()).isNotNull();
    }

}
