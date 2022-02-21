package io.github.angrylid.mall.api.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.github.angrylid.mall.api.annotation.AdminController;
import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.dto.auth.AdminSignIn;
import io.github.angrylid.mall.service.AdminService;

/**
 * 管理员鉴权控制器
 */
@Validated
@AdminController("/auth")
public class AuthApi {

    @Autowired
    private AdminService adminService;

    /**
     * 管理员登录
     * 
     * @param adminSignIn 管理员登录信息
     * @return
     */
    @PostMapping()
    public CustomResponse<String> signIn(
            @RequestBody @Valid AdminSignIn requestBody) {
        try {
            String token = adminService.generateToken(requestBody.getName(), requestBody.getPassword());
            return CustomResponse.success(token);
        } catch (Exception ex) {
            return CustomResponse.dbException(ex.getMessage());
        }
    }
}