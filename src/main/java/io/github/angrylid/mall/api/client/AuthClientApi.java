package io.github.angrylid.mall.api.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.dto.UserLoginDto;
import io.github.angrylid.mall.service.UserService;

@RestController
@RequestMapping("/client/sign")
public class AuthClientApi {

    @Autowired
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 客户端用户登录方法
     * 
     * @param userLoginDto  电话号码和密码
     * @param bindingResult 校验结果
     * @return JWT
     */
    @PostMapping("/in")
    public CustomResponse<String> signIn(@RequestBody @Validated UserLoginDto userLoginDto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                logger.warn("fieldError:{}", fieldError);
            }
            return CustomResponse.validException("输入的参数不正确");
        }
        String token;
        try {
            token = this.userService.generateToken(userLoginDto.getTelephone(),
                    userLoginDto.getPassword());
        } catch (IllegalArgumentException e) {
            token = e.getMessage();
            return CustomResponse.unauthorized(token);
        }
        return CustomResponse.success(token);
    }

    /**
     * 客户端用户注册方法
     * 
     * @param userLoginDto  电话号码和密码
     * @param bindingResult 校验结果
     * @return JWT
     */
    @PostMapping("/up")
    public CustomResponse<String> signUp(@RequestBody @Validated UserLoginDto userLoginDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                logger.warn("fieldError:{}", fieldError);
            }
            return CustomResponse.validException("输入的参数不正确");
        }

        try {
            this.userService.addUser(userLoginDto.getTelephone(),
                    userLoginDto.getPassword(), "User" + userLoginDto.getTelephone());
        } catch (Exception e) {
            return CustomResponse.unauthorized(e.getMessage());
        }
        String token = userService.generateToken(userLoginDto.getTelephone(), userLoginDto.getPassword());

        return CustomResponse.success(token);
    }
}
