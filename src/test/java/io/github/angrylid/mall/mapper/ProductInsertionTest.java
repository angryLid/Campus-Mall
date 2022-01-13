package io.github.angrylid.mall.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.angrylid.mall.generated.entity.Product;
import io.github.angrylid.mall.generated.mapper.ProductMapper;

@SpringBootTest
public class ProductInsertionTest {
    @Autowired
    ProductMapper productMapper;

    static StringBuilder stringBuilder;

    Product product;

    @BeforeAll
    static void beforeAll() {
        stringBuilder = new StringBuilder();

        for (int i = 0; i < 50; i++) {
            stringBuilder.append("2022-01-08");
        }

    }

    @BeforeEach
    void beforeEach() {
        product = new Product();
        product.setTitle(stringBuilder.toString().substring(0, 50));
        product.setDescription(stringBuilder.toString());
        product.setIsDeleted(true);
        product.setSellerId(10002);
    }

    @AfterEach
    void afterEach() {
        product = null;
    }

    @Test
    void testPrice() {
        product.setPrice(new BigDecimal(12.123456));
        productMapper.insert(product);
        assertThat(product.getId()).isNotNull();
    }

    @Test
    void testPrice0() {
        product.setPrice(new BigDecimal(12.5099999));
        productMapper.insert(product);
        assertThat(product.getId()).isNotNull();
    }

    @Test
    void testPrice1() {
        product.setPrice(new BigDecimal(12.999999));
        productMapper.insert(product);
        assertThat(product.getId()).isNotNull();
    }

    @Test
    void testPrice2() {
        product.setPrice(new BigDecimal(12.000000000));
        productMapper.insert(product);
        assertThat(product.getId()).isNotNull();
    }

}
