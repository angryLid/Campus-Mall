package io.github.angrylid.mall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.angrylid.mall.generated.entity.Cart;
import io.github.angrylid.mall.generated.mapper.CartMapper;

@Service
public class CartService {
    private CartMapper cartMapper;

    public CartService(@Autowired CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    public void insertOne(Integer userId, Integer productId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setProductId(productId);
        cartMapper.insert(cart);
    }
}
