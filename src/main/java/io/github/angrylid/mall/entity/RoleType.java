package io.github.angrylid.mall.entity;

public enum RoleType {
    UNKNOWN("unknown"),
    STUDENT_UNVERIFIED("student_unverified"),
    STUDENT_VERIFIED("student_verified"),
    RETAILER_UNVERIFIED("retailer_unverified"),
    RETAILER_VERIFIED("retailer_verified");

    private final String status;

    RoleType(String status) {
        this.status = status;
    }

    public String getValue() {
        return status;
    }

}
