package io.spring.guides.dto;

public enum Code {
    OK(200, "OK"),
    UNAUTHORIZED(401, "Unauthorized"),

    VALIDATION_EXCEPTION(40000, "Validation Exception"),
    DB_EXCEPTION(50000, "Database Exception");

    private final int code;
    private final String message;

    Code(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

}
