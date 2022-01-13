package io.github.angrylid.mall.api;

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

    @PostMapping(value = "/", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @ApiOperation("测试上传")
    public CustomResponse<Object> upload(@ModelAttribute PostProductDto postProductDto) throws IllegalAccessException {

        String priceRe = "^\\d{1,4}(\\.\\d{2})?$";
        if (!postProductDto.getPrice().matches(priceRe)) {
            return CustomResponse.validException("Invalid Paramters.");
        }

        try {
            productService.addProduct(postProductDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CustomResponse.success(postProductDto.getTitle());
    }
}
