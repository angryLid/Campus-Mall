package io.github.angrylid.mall.api.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
    public CustomResponse<String> add() {
        return CustomResponse.dbException("data");
    }
}
