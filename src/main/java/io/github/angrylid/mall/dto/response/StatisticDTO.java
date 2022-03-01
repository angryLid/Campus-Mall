package io.github.angrylid.mall.dto.response;

public class StatisticDTO {
    private Long userCount;
    private Long productCount;
    private Long merchantCount;
    private Long orderCount;

    public StatisticDTO(Long userCount, Long productCount, Long merchantCount, Long orderCount) {
        this.userCount = userCount;
        this.productCount = productCount;
        this.merchantCount = merchantCount;
        this.orderCount = orderCount;
    }

    public StatisticDTO() {
    }

    public Long getUserCount() {
        return userCount;
    }

    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }

    public Long getProductCount() {
        return productCount;
    }

    public void setProductCount(Long productCount) {
        this.productCount = productCount;
    }

    public Long getMerchantCount() {
        return merchantCount;
    }

    public void setMerchantCount(Long merchantCount) {
        this.merchantCount = merchantCount;
    }

    public Long getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Long orderCount) {
        this.orderCount = orderCount;
    }

}
