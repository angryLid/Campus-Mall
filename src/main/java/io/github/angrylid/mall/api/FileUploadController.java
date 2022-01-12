package io.github.angrylid.mall.api;

import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.dto.PostProductDto;
import io.github.angrylid.mall.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;

@Api(tags = "文件上传")
@RestController()
@RequestMapping(path = "/upload")
public class FileUploadController {

    @Autowired
    ProductService productService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping(value = "/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation("测试上传")
    public CustomResponse<Object> upload(@ModelAttribute PostProductDto postProductDto) throws IllegalAccessException {

        Field[] fields = postProductDto.getClass().getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);

            Object value = f.get(postProductDto);
            System.out.println(f.getName()+value);
        }


        try {
            productService.addProduct(postProductDto.getImage0());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CustomResponse.success(postProductDto.getTitle());
    }
}
