package io.github.angrylid.mall.api.client;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.github.angrylid.mall.api.annotation.ClientController;
import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.dto.auth.UserSignIn;
import io.github.angrylid.mall.service.UserService;

@Validated
@ClientController("/auth")
public class AuthClientApi {

    private UserService userService;

    @Autowired
    public AuthClientApi(UserService userService) {
        this.userService = userService;
    }

    /**
     * 客户端用户登录方法
     * 
     * @param userLoginDto  电话号码和密码
     * @param bindingResult 校验结果
     * @return JWT
     */
    @PostMapping()
    public CustomResponse<String> signIn(@RequestBody @Valid UserSignIn userLoginDto) {

        try {
            String token = userService.generateToken(userLoginDto.getTelephone(),
                    userLoginDto.getPassword());
            return CustomResponse.success(token);
        } catch (IllegalArgumentException e) {
            return CustomResponse.unauthorized(e.getMessage());
        }

    }

    /**
     * 客户端用户注册方法
     * 
     * @param userLoginDto  电话号码和密码
     * @param bindingResult 校验结果
     * @return JWT
     */
    @PostMapping("/register")
    public CustomResponse<String> signUp(@RequestBody @Valid UserSignIn userLoginDto) {

        try {
            userService.addUser(userLoginDto.getTelephone(),
                    userLoginDto.getPassword(), "User" + userLoginDto.getTelephone());
            String token = userService.generateToken(userLoginDto.getTelephone(), userLoginDto.getPassword());
            return CustomResponse.success(token);
        } catch (Exception e) {
            return CustomResponse.unauthorized(e.getMessage());
        }

    }
}
