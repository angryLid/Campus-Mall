package io.github.angrylid.mall.api;

import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.dto.RegisterDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "注册模块")
@RestController
@RequestMapping(path = "/register")
public class Register {

    @PostMapping
    public CustomResponse<String> registerNewUser(@RequestBody @Validated RegisterDto registerDto){
        return CustomResponse.success("注册成功");
    }
}
