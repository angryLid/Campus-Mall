package io.github.angrylid.mall.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.angrylid.mall.dto.request.OrderDTO;
import io.github.angrylid.mall.entity.DetailOrder;
import io.github.angrylid.mall.generated.entity.Cart;
import io.github.angrylid.mall.generated.entity.Product;
import io.github.angrylid.mall.generated.entity.TradeOrder;
import io.github.angrylid.mall.generated.mapper.CartMapper;
import io.github.angrylid.mall.generated.mapper.ProductMapper;
import io.github.angrylid.mall.generated.mapper.TradeOrderMapper;
import io.github.angrylid.mall.mapper.DetailOrderMapper;

@Service
public class TradeOrderService {

    private TradeOrderMapper tradeOrderMapper;
    private ProductMapper productMapper;
    private DetailOrderMapper detailOrderMapper;
    private CartMapper cartMapper;

    @Autowired
    public TradeOrderService(TradeOrderMapper tradeOrderMapper, ProductMapper productMapper,
            DetailOrderMapper detailOrderMapper, CartMapper cartMapper) {
        this.tradeOrderMapper = tradeOrderMapper;
        this.productMapper = productMapper;
        this.detailOrderMapper = detailOrderMapper;
        this.cartMapper = cartMapper;
    }

    public List<DetailOrder> selectSold(Integer userId) {
        return detailOrderMapper.selectSold(userId);
    }

    public List<DetailOrder> selectBought(Integer userId) {
        return detailOrderMapper.selectBought(userId);
    }

    public Integer insertOrders(Integer userId, List<OrderDTO> orders) {

        for (OrderDTO order : orders) {
            Cart cart = cartMapper.selectOne(new QueryWrapper<Cart>().eq("id", order.getId()));
            Product product = productMapper.selectOne(new QueryWrapper<Product>().eq("id", cart.getProductId()));
            TradeOrder tradeOrder = new TradeOrder();
            tradeOrder.setSellerId(product.getSellerId());
            tradeOrder.setConsumerId(userId);
            tradeOrder.setProductId(order.getId());
            tradeOrder.setStatus(1);
            tradeOrder.setQuantity(order.getQuantity());
            tradeOrderMapper.insert(tradeOrder);
        }
        return 1;
    }
}
