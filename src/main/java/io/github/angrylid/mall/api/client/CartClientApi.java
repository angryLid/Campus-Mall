package io.github.angrylid.mall.api.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.angrylid.mall.dto.CartItemDto;
import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.jwt.annotation.TokenRequired;
import io.github.angrylid.mall.service.CartService;

@RestController
@RequestMapping("/client/cart/")
public class CartClientApi {

    private CartService cartService;

    public CartClientApi(@Autowired CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * 添加商品到购物车
     * 
     * @param productId 商品id
     * @param userId    用户id
     * @return 添加成功返回true，否则返回false
     */
    @TokenRequired
    @PostMapping("/{id}")
    public CustomResponse<String> add(@PathVariable("id") Integer productId,
            @RequestAttribute("id") Integer userId) {
        Integer row = cartService.insertOne(userId, productId);
        if (row == 1) {
            return CustomResponse.success("success");
        } else {
            return CustomResponse.dbException("fail");
        }
    }

    /**
     * 更新购物车中的商品数量 - 1
     * 
     * @param productId
     * @param userId
     * @return
     */
    @TokenRequired
    @PutMapping("/{id}")
    public CustomResponse<String> update(@PathVariable("id") Integer productId,
            @RequestAttribute("id") Integer userId) {
        Integer row = cartService.updateOne(userId, productId);
        if (row == 1) {
            return CustomResponse.success("success");
        } else {
            return CustomResponse.dbException("fail");
        }
    }

    /**
     * 获取我的购物车所有商品
     * 
     * @param userId
     * @return
     */
    @TokenRequired
    @GetMapping("/")
    public CustomResponse<List<CartItemDto>> getMyCart(@RequestAttribute("id") Integer userId) {
        List<CartItemDto> carts = cartService.selectOnes(userId);
        return CustomResponse.success(carts);
    }

}
