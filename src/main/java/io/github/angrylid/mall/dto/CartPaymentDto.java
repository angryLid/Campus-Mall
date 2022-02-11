package io.github.angrylid.mall.dto;

import java.util.List;

public class CartPaymentDto {
    private List<Integer> items;

    public List<Integer> getItems() {
        return items;
    }

    public void setItems(List<Integer> items) {
        this.items = items;
    }

}
