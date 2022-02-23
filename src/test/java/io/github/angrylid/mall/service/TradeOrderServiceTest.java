package io.github.angrylid.mall.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.angrylid.mall.entity.DetailOrder;

@SpringBootTest

public class TradeOrderServiceTest {

    private TradeOrderService tradeOrderService;

    @Autowired
    public TradeOrderServiceTest(TradeOrderService tradeOrderService) {
        this.tradeOrderService = tradeOrderService;
    }

    @Test
    void testSelectBought() {
        List<DetailOrder> list = tradeOrderService.selectBought(237);
        System.out.println(list);

    }

    @Test
    void testSelectSold() {
        List<DetailOrder> list = tradeOrderService.selectSold(231);
        System.out.println(list);
    }
}
