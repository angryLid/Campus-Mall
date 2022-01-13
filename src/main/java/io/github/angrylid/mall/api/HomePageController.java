package io.github.angrylid.mall.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.angrylid.mall.dto.CustomResponse;

@RestController()
@RequestMapping(path = "/home")
public class HomePageController {

    @GetMapping(value = "/")
    public CustomResponse<Object> getRecentProduct() {
        return CustomResponse.success("products");
    }

}
