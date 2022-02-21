package io.github.angrylid.mall.api;

import javax.validation.ConstraintViolationException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.angrylid.mall.dto.CustomResponse;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public CustomResponse<String> deniedPermissionException(IllegalArgumentException ex) {
        return CustomResponse.unauthorized(ex.getMessage());
    }

    @ExceptionHandler(value = { ConstraintViolationException.class })
    public CustomResponse<String> constrantViolationException(ConstraintViolationException ex) {
        return CustomResponse.validException("数据校验失败,请检查数据是否符合要求");
    }

}