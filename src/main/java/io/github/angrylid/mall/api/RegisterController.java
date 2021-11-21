package io.github.angrylid.mall.api;

import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.dto.RegisterDto;
import io.github.angrylid.mall.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "注册模块")
@RestController
@RequestMapping(path = "/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    public RegisterController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @ApiOperation("注册方法")
    @PostMapping
    public CustomResponse<String> registerNewUser(@RequestBody @Validated RegisterDto registerDto) {

        try {
            this.userService.registerUser(registerDto.getTelephone(), registerDto.getPassword());
        } catch (Exception e) {
            return CustomResponse.success("参数错误");
        }
        return CustomResponse.success("注册成功");
    }
}
