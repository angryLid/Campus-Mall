package io.github.angrylid.mall.mapper;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import io.github.angrylid.mall.generated.entity.Product;
import io.github.angrylid.mall.generated.mapper.ProductMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@MybatisPlusTest
public class ProductInsertionTest {
    @Autowired
    private ProductMapper productMapper;

    @Test
    void testInsert() {
        var product = new Product();
        productMapper.insert(product);
        assertEquals(2, product.getId());
    }

}
