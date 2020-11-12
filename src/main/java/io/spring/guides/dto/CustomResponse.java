package io.spring.guides.dto;

public class CustomResponse<T> {
    private int code;
    private String message;
    private T data;

    protected CustomResponse(){}

    protected CustomResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public static <T> CustomResponse<T> success(T data) {
        return new CustomResponse<T>(Code.OK.getCode(), Code.OK.getMessage(), data);
    }

    public static <T> CustomResponse<T> unauthorized(T data) {
        return new CustomResponse<T>(Code.UNAUTHORIZED.getCode(), Code.UNAUTHORIZED.getMessage(), data);
    }

    public static <T> CustomResponse<T> unauthorized(T data,String message) {
        return new CustomResponse<T>(Code.UNAUTHORIZED.getCode(), message, data);
    }

    public static <T> CustomResponse<T> dbException(T data) {
        return new CustomResponse<T>(Code.DB_EXCEPTION.getCode(),Code.DB_EXCEPTION.getMessage(), data);
    }
    public static <T> CustomResponse<T> validException(T data) {
        return new CustomResponse<T>(Code.VALIDATION_EXCEPTION.getCode(), Code.VALIDATION_EXCEPTION.getMessage(), data);
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
