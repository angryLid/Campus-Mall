package io.github.angrylid.mall.api;

import io.github.angrylid.mall.dto.CustomResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public CustomResponse<String> deniedPermissionException(IllegalArgumentException ex) {
        return CustomResponse.unauthorized(ex.getMessage());
    }

}