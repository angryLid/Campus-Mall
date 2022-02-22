package io.github.angrylid.mall.api.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import io.github.angrylid.mall.api.annotation.AdminController;
import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.generated.entity.Product;
import io.github.angrylid.mall.jwt.annotation.AdminRequired;
import io.github.angrylid.mall.service.ProductService;

@AdminController("/product")
public class ProductApi {

    private ProductService productService;

    @Autowired
    public ProductApi(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 查询所有商品
     * GET /admin/product
     * 
     * @return
     */
    @AdminRequired
    @GetMapping()
    public CustomResponse<?> getProducts() {
        List<Product> products = productService.selectProducts();
        return CustomResponse.success(products);
    }

    /**
     * 更新商品状态
     * PUT /admin/product/{id}/{status}
     * 
     * @param id
     * @param status
     * @return
     */
    @AdminRequired
    @PutMapping("/{id}/{status}")
    public CustomResponse<?> putProduct(@PathVariable("id") Integer id,
            @PathVariable("status") Integer status) {

        Integer result = productService.updateProductStatus(id, status);
        if (result != 1) {
            CustomResponse.dbException("更新商品失败");
        }
        return CustomResponse.success("更新商品成功");
    }

}
