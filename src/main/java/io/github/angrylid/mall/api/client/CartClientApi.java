package io.github.angrylid.mall.api.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
