package io.github.angrylid.mall.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.angrylid.mall.generated.entity.Product;

@SpringBootTest
public class ProductServiceTest {

    private ProductService productService;

    @Autowired
    public ProductServiceTest(ProductService productService) {
        this.productService = productService;
    }

    @Test
    void testSelectProductPage() throws InterruptedException {
        List<Product> products = productService.selectProductPage(1);
        System.out.println(products);
        TimeUnit.SECONDS.sleep(30);

        products = productService.selectProductPage(2);
        System.out.println(products);

    }
}
