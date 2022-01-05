package io.github.angrylid.mall.api;

import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.dto.UserLoginDto;
import io.github.angrylid.mall.entity.AccountInformation;
import io.github.angrylid.mall.jwt.annotation.TokenRequired;
import io.github.angrylid.mall.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "登录/注册模块")
@RestController
@RequestMapping(path = "/user")
public class LogInController {

    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(LogInController.class);

    @ApiOperation("测试连通")
    @GetMapping("/")
    public String ping() {
        return "<h1>Ping</h1>";
    }

    @ApiOperation("登录方法")
    @PostMapping("/signin")
    public CustomResponse<String> login(@RequestBody
                                        @Validated UserLoginDto userLoginDto,
                                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                logger.warn("fieldError:{}", fieldError);
            }
            return CustomResponse.validException("输入的参数不正确");
        }
        String token;
        try {
            token = this.userService.login(userLoginDto.getTelephone(),
                    userLoginDto.getPassword());
        } catch (IllegalArgumentException e) {
            token = e.getMessage();
            return CustomResponse.unauthorized(token);
        }
        return CustomResponse.success(token);
    }

    @ApiOperation("注册方法")
    @PostMapping("/signup")
    public CustomResponse<String> register(@RequestBody @Validated UserLoginDto userLoginDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                logger.warn("fieldError:{}", fieldError);
            }
            return CustomResponse.validException("输入的参数不正确");
        }
        String result;
        try {
            result = this.userService.addUser(userLoginDto.getTelephone(),
                    userLoginDto.getPassword(), "User" + userLoginDto.getTelephone());
        } catch (Exception e) {
            return CustomResponse.unauthorized(e.getMessage());
        }
        return CustomResponse.success(result);
    }

    @TokenRequired
    @ApiOperation("获取当前用户信息")
    @GetMapping("/myaccount")
    public CustomResponse<AccountInformation> getCurrentUserInformation(@RequestAttribute("identity") String identify) {

        var friend = this.userService.getFollowingAndFollowedOfCurrentUser(Integer.parseInt(identify));

        return CustomResponse.success(friend);
    }
}
