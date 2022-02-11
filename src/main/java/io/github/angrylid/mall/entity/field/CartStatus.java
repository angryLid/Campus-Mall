package io.github.angrylid.mall.entity.field;

public enum CartStatus {
    IN_CART("in_cart"),
    DELETED("deleted"),
    WAITING_RECEIVE("waiting_receive");

    private final String status;

    CartStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
