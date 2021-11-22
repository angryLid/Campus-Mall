package io.github.angrylid.mall.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Api(tags = "账户控制")
@RestController
@RequestMapping(path = "/account")
public class AccountController {
    
    @ApiOperation("注册账户")
    @PostMapping("/register")
    public String register() {
        //TODO: process POST request
        
        return "register";
    }

    @ApiOperation("忘记密码")
    @PostMapping( "/reset")
    public String findMyPassword(){
        return "forget";
    }
    
    @ApiOperation("登录")
    @PostMapping("/login")
    public String logIn(){
        return "IN";
    }

    @ApiOperation("登出")
    @PostMapping("/logout")
    public String logOut(){
        return "OUT";
    }
}
