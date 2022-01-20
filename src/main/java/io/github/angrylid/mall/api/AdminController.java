package io.github.angrylid.mall.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.angrylid.mall.dto.CustomResponse;

@RestController()
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/")
    public CustomResponse<Object> getRecentProduct(
            @RequestParam("name") String name,
            @RequestParam("password") String password) {
        return CustomResponse.success(name + password + "is trying");
    }

}
