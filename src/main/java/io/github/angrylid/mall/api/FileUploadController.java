package io.github.angrylid.mall.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.dto.PostProductDto;
import io.github.angrylid.mall.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "文件上传")
@RestController()
@RequestMapping(path = "/upload")
public class FileUploadController {

    @Autowired
    ProductService productService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping(value = "/", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @ApiOperation("测试上传")
    public CustomResponse<Object> upload(@ModelAttribute PostProductDto postProductDto) throws IllegalAccessException {

        try {
            productService.addProduct(postProductDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CustomResponse.success(postProductDto.getTitle());
    }
}
