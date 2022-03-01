package io.github.angrylid.mall.api.client;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;

import io.github.angrylid.mall.api.annotation.ClientController;
import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.dto.request.OrderDTO;
import io.github.angrylid.mall.dto.response.DetailOrder;
import io.github.angrylid.mall.service.CartService;
import io.github.angrylid.mall.service.TradeOrderService;

@Validated
@ClientController("/order")
public class OrderClientApi {

    private TradeOrderService tradeOrderService;
    private CartService cartService;

    @Autowired
    public OrderClientApi(TradeOrderService tradeOrderService, CartService cartService) {
        this.tradeOrderService = tradeOrderService;
        this.cartService = cartService;
    }

    @GetMapping("/sold")
    public CustomResponse<?> getSold(@RequestAttribute("id") Integer userId) {
        List<DetailOrder> tradeOrders = tradeOrderService.selectSold(userId);
        return CustomResponse.success(tradeOrders);
    }

    @GetMapping("/bought")
    public CustomResponse<?> getBought(@RequestAttribute("id") Integer userId) {
        List<DetailOrder> tradeOrders = tradeOrderService.selectBought(userId);
        return CustomResponse.success(tradeOrders);
    }

    /**
     * 下单
     * POST /{version}/order/payment
     * 
     * @param userId
     * @param uploadOrder
     * @return
     * 
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/payment")
    public CustomResponse<?> pay(@RequestAttribute("id") Integer id,
            @RequestBody List<@Valid OrderDTO> orders) {
        cartService.updateOnes(id, orders);
        tradeOrderService.insertOrders(id, orders);

        return CustomResponse.success("data");
    }

}
