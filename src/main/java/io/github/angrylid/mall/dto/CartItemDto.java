package io.github.angrylid.mall.dto;

import java.math.BigDecimal;

public class CartItemDto {

    private Integer cartId;

    private Integer id;

    private String title;

    private BigDecimal price;

    private Integer productSum;

    private String image0;

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getProductSum() {
        return productSum;
    }

    public void setProductSum(Integer productSum) {
        this.productSum = productSum;
    }

    public String getImage0() {
        return image0;
    }

    public void setImage0(String image0) {
        this.image0 = image0;
    }

    public CartItemDto() {

    }

    public CartItemDto(Integer id, String title, BigDecimal price, Integer productSum, String image0) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.productSum = productSum;
        this.image0 = image0;
    }

}
