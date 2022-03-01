package io.github.angrylid.mall.api.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

import io.github.angrylid.mall.api.annotation.ClientController;
import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.dto.request.PostProductDto;
import io.github.angrylid.mall.dto.response.ProductDetailsDTO;
import io.github.angrylid.mall.generated.entity.Product;
import io.github.angrylid.mall.jwt.annotation.TokenRequired;
import io.github.angrylid.mall.service.ProductService;

@ClientController("/product")
public class ProductClientApi {

    private ProductService productService;

    public ProductClientApi(@Autowired ProductService productService) {
        this.productService = productService;
    }

    /**
     * 获取个人发布闲置的物品
     * GET /client/product/
     * 
     * @return
     */
    @GetMapping("/")
    public CustomResponse<List<Product>> getRecentProducts(@RequestAttribute("id") Integer id) {
        if (id == null) {
            List<Product> products = productService.selectUserProducts();
            return CustomResponse.success(products);
        }
        List<Product> products = productService.selectUserProducts(id);
        return CustomResponse.success(products);
    }

    /**
     * 获取指定的商品
     * GET /client/product/{id}
     * 
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public CustomResponse<?> getSpecificProduct(@RequestAttribute("id") Integer userId,
            @PathVariable("id") String productId) {
        ProductDetailsDTO product;
        if (userId == null) {
            product = productService.getProductAndSeller(productId);
        } else {
            product = productService.getProductAndSeller(userId, productId);
        }

        return CustomResponse.success(product);

    }

    /**
     * 获取店铺发布的商品
     * GET /client/product/retailer/
     * 
     * @param id
     * @return
     */
    @GetMapping("/retailer")
    public CustomResponse<List<Product>> getRetailerProducts(@RequestAttribute("id") Integer id) {
        if (id == null) {
            List<Product> products = productService.selectRtrProducts();
            return CustomResponse.success(products);
        }
        List<Product> products = productService.selectRtrProducts(id);
        return CustomResponse.success(products);
    }

    /**
     * 发布一个新的商品
     * POST /client/product/
     * 
     * @param postProductDto 商品信息
     * @return
     * @throws IllegalAccessException
     */
    @TokenRequired
    @PostMapping(value = "/", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public CustomResponse<String> upload(@ModelAttribute PostProductDto postProductDto,
            @RequestAttribute("id") Integer id) throws IllegalAccessException {

        String priceRe = "^\\d{1,4}(\\.\\d{1,2})?$";
        if (!postProductDto.getPrice().matches(priceRe)) {
            return CustomResponse.validException("Invalid Paramters.");
        }

        try {
            productService.addProduct(postProductDto, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CustomResponse.success(postProductDto.getTitle());
    }

    /**
     * 查询发布人的联系方式
     * GET /client/product/seller/{id}
     * 
     * @param id
     * @return
     */
    @TokenRequired
    @GetMapping("/seller/{id}")
    public CustomResponse<?> getSeller(@PathVariable Integer id) {
        String tel = productService.selectSellerTel(id);
        return CustomResponse.success(tel);
    }

    /**
     * 查询我的发布
     * GET /client/product/published/
     * 
     * @param id
     * @return
     */
    @TokenRequired
    @GetMapping("/published")
    public CustomResponse<?> getMyPublished(@RequestAttribute("id") Integer id) {
        List<Product> products = productService.selectMyPublished(id);
        return CustomResponse.success(products);
    }

    /**
     * 删除我的发布
     * DELETE /client/product/{id}
     * 
     * @param id
     * @return
     */
    @TokenRequired
    @DeleteMapping("/{id}")
    public CustomResponse<?> deleteProduct(@PathVariable("id") Integer id) {
        Integer result = productService.deleteProduct(id);
        if (result == 1) {
            return CustomResponse.success("Delete Success.");
        }
        return CustomResponse.dbException("database error.");
    }

    @TokenRequired
    @GetMapping("/favorite")
    public CustomResponse<?> getFavorite(@RequestAttribute("id") Integer id) {
        List<Product> products = productService.selectFavorite(id);
        return CustomResponse.success(products);
    }

    @TokenRequired
    @PostMapping("/favorite/{id}")
    public CustomResponse<?> addFavorite(@PathVariable("id") Integer id, @RequestAttribute("id") Integer userId) {
        Integer result = productService.addFavorite(id, userId);
        if (result == 1) {
            return CustomResponse.success("Add Success.");
        }
        return CustomResponse.dbException("database error.");
    }

}
