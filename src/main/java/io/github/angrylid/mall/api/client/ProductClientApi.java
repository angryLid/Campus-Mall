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

    @Autowired
    ProductService productService;

    /**
     * 获取个人闲置的物品
     * 
     * @return
     */
    @GetMapping("/")
    public CustomResponse<List<Product>> getRecentProducts() {
        var resp = productService.selectUserProducts();
        return CustomResponse.success(resp);
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
     * 获取小店的商品
     * 
     * @return
     */
    @GetMapping("/retailer")
    public CustomResponse<List<Product>> getRetailerProducts() {
        List<Product> products = productService.selectRtrProducts();
        return CustomResponse.success(products);
    }

    /**
     * 客户端新增一条数据
     * 
     * @param postProductDto
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
}
