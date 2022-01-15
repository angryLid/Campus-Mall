package io.github.angrylid.mall.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.angrylid.mall.entity.ProductDetail;

@SpringBootTest
public class ProductDetailMapperTest {

    @Autowired
    ProductDetailMapper mapper;

    @Test
    public void testSelectList() {
        List<ProductDetail> productDetails = mapper.selectList();

        assertThat(productDetails.get(0).getPid()).isEqualTo(9);
    }

    @Test
    public void testTelectOneById() {
        ProductDetail products = mapper.selectOneById(9);

        assertThat(products.getUid()).isEqualTo(10001);
    }

}
