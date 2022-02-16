package io.github.angrylid.mall.api.client;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.dto.PostProductDto;
import io.github.angrylid.mall.generated.entity.Product;
import io.github.angrylid.mall.jwt.annotation.TokenRequired;
import io.github.angrylid.mall.service.ProductService;

@RestController()
@RequestMapping("/client/product")
public class ProductClientApi {

    private ProductService productService;

    public ProductClientApi(@Autowired ProductService productService) {
        this.productService = productService;
    }

    /**
     * 获取个人闲置的物品
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
     * 获取特定的商品
     * 
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public CustomResponse<Map<String, Object>> getSpecificProduct(@PathVariable("id") String id) {

        var resp = productService.getProductAndSeller(id);
        return CustomResponse.success(resp);

    }

    /**
     * 获取店铺的商品
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
     * 
     * @param postProductDto 商品信息
     * @return
     * @throws IllegalAccessException
     */
    @TokenRequired
    @PostMapping(value = "/", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public CustomResponse<String> upload(@ModelAttribute PostProductDto postProductDto,
            @RequestAttribute("id") Integer id) throws IllegalAccessException {

        String priceRe = "^\\d{1,4}(\\.\\d{2})?$";
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

}
