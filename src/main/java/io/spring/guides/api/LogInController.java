package io.spring.guides.api;

import io.spring.guides.dto.Code;
import io.spring.guides.dto.CustomResponse;
import io.spring.guides.dto.UserLoginDto;
import io.spring.guides.jwt.JwtUtil;
import io.spring.guides.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "登录模块")
@RestController
@RequestMapping(path = "/login")
public class LogInController {

    @Autowired
    UserService userService;

    @ApiOperation("登录方法")
    @PostMapping()
    public CustomResponse<String> login(@RequestBody UserLoginDto userLoginDto) {

        String token = "";
        try {
            token = this.userService.login(userLoginDto.getJobNumber(),
                    userLoginDto.getPassword());
        } catch (IllegalArgumentException e) {
            token = e.getMessage();
            return CustomResponse.unauthorized(token);
        }

        return CustomResponse.success(token);
    }
}
