package io.github.angrylid.mall.api;

import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.dto.PostProductDto;
import io.github.angrylid.mall.dto.UserLoginDto;
import io.github.angrylid.mall.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "文件上传")
@RestController()
@RequestMapping(path = "/upload")
public class FileUploadController {

    @Autowired
    ProductService productService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping("/")
    @ApiOperation("测试上传")
    public CustomResponse<String> upload(@RequestBody @Validated PostProductDto postProductDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                logger.warn("fieldError:{}", fieldError);
            }
            return CustomResponse.validException("输入的参数不正确");
        }
        String result;
        try {
            result = this.productService.addProduct(postProductDto.getTitle(), postProductDto.getDescription(), postProductDto.getImages(), postProductDto.getPrice());
        } catch (Exception e) {
            return CustomResponse.unauthorized(e.getMessage());
        }
        return CustomResponse.success(result);
    }


}
