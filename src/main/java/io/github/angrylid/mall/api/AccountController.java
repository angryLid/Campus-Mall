package io.github.angrylid.mall.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.dto.RegisterDto;
import io.github.angrylid.mall.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "账户控制")
@RestController
@RequestMapping(path = "/account")
public class AccountController {

    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    AccountService accountService;

    @ApiOperation("注册账户")
    @PostMapping("/register")
    public CustomResponse<String> register(@RequestBody @Validated RegisterDto registerDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for(FieldError fieldError:bindingResult.getFieldErrors()){
                logger.error("FieldError: {}", fieldError);
            }
            return CustomResponse.validException("Illegel Parameters.");
        }

        try {
            this.accountService.register(registerDto.getTelephone(), registerDto.getPassword());
            return CustomResponse.success("registered.");
        } catch (Exception e) {

        }
        return CustomResponse.dbException("failed to store");
    }

    @ApiOperation("忘记密码")
    @PostMapping("/reset")
    public String findMyPassword() {
        return "forget";
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public String logIn() {
        return "IN";
    }

    @ApiOperation("登出")
    @PostMapping("/logout")
    public String logOut() {
        return "OUT";
    }
}
