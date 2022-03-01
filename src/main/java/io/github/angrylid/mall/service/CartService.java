package io.github.angrylid.mall.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.angrylid.mall.dto.request.OrderDTO;
import io.github.angrylid.mall.dto.response.CartItemDto;
import io.github.angrylid.mall.entity.CartStatus;
import io.github.angrylid.mall.generated.entity.Cart;
import io.github.angrylid.mall.generated.entity.Product;
import io.github.angrylid.mall.generated.mapper.CartMapper;
import io.github.angrylid.mall.generated.mapper.ProductMapper;

@Service
public class CartService {

    private CartMapper cartMapper;

    private ProductMapper productMapper;

    public CartService(@Autowired CartMapper cartMapper,
            @Autowired ProductMapper productMapper) {
        this.cartMapper = cartMapper;
        this.productMapper = productMapper;
    }

    /**
     * 添加商品到购物车
     * 
     * @param userId    用户id
     * @param productId 商品id
     * @return 生效的行数
     */
    public Integer insertOne(Integer userId, Integer productId) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("product_id", productId);
        queryWrapper.eq("status", CartStatus.IN_CART.getStatus());
        Cart cart = cartMapper.selectOne(queryWrapper);

        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setProductId(productId);
            cart.setProductSum(1);
            cart.setStatus(CartStatus.IN_CART.getStatus());
            return cartMapper.insert(cart);
        } else {
            cart.setProductSum(cart.getProductSum() + 1);
            return cartMapper.updateById(cart);
        }
    }

    /**
     * 商品数量 -1
     * 
     * @param userId
     * @param productId
     * @return
     */
    public Integer updateOne(Integer userId, Integer productId) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("product_id", productId);
        queryWrapper.eq("status", CartStatus.IN_CART.getStatus());
        Cart cart = cartMapper.selectOne(queryWrapper);
        if (cart != null) {
            cart.setProductSum(cart.getProductSum() - 1);
            return cartMapper.updateById(cart);
        }
        return 0;
    }

    /**
     * 获取我的购物车所有商品
     * 
     * @param userId 用户id
     * @return 我的购物车所有商品
     */
    public List<CartItemDto> selectOnes(Integer userId) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("status", CartStatus.IN_CART.getStatus());
        queryWrapper.gt("product_sum", 0);

        List<Cart> carts = cartMapper.selectList(queryWrapper);
        List<CartItemDto> cartItemDtos = new ArrayList<>();

        for (Cart cart : carts) {
            Product product = productMapper.selectById(cart.getProductId());
            CartItemDto cartItemDto = new CartItemDto(cart.getProductId(), product.getTitle(),
                    product.getPrice(), cart.getProductSum(), product.getImage0());
            cartItemDto.setCartId(cart.getId());
            cartItemDtos.add(cartItemDto);
        }
        return cartItemDtos;

    }

    /**
     * 清空购物车
     * 
     * @param userId
     * @param items
     */
    public void updateOnes(Integer userId, List<OrderDTO> items) {

        for (OrderDTO item : items) {
            Cart cart = new Cart();
            cart.setId(item.getId());
            cart.setStatus(CartStatus.WAITING_RECEIVE.getStatus());
            cart.setGeneratedAt(LocalDateTime.now());
            cartMapper.updateById(cart);
        }
    }

    /**
     * 移除一项
     * 
     * @param userId
     * @param cartId
     * @return
     */
    public Integer deleteOne(Integer userId, Integer cartId) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("id", cartId);
        queryWrapper.eq("status", CartStatus.IN_CART.getStatus());
        Cart cart = cartMapper.selectOne(queryWrapper);
        if (cart != null) {
            cart.setProductSum(0);
            return cartMapper.updateById(cart);
        }
        return 0;
    }

}
