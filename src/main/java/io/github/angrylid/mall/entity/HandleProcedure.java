package io.github.angrylid.mall.entity;

public enum HandleProcedure {
    WAITING("waiting"),
    APPROVED("approved"),
    REJECTED("rejected");

    private final String status;

    HandleProcedure(String status) {
        this.status = status;
    }

    public String getValue() {
        return this.status;
    }
}
