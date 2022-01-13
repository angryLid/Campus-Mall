package io.github.angrylid.mall.mapper;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.angrylid.mall.generated.entity.Product;
import io.github.angrylid.mall.generated.mapper.ProductMapper;

@SpringBootTest
public class SelectProductTest {

    @Autowired
    ProductMapper productMapper;

    @Test
    void testSelectList() {
        // SELECT * FROM product;
        List<Product> products = productMapper.selectList(null);

        products.forEach(p -> System.out.println(p.getTitle()));
    }
}
