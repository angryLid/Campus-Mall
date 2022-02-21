package io.github.angrylid.mall.api.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.angrylid.mall.api.annotation.AdminController;
import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.service.AdminService;

/**
 * 管理员鉴权控制器
 */
@AdminController("/auth")
public class AuthApi {

    @Autowired
    private AdminService adminService;

    /**
     * 管理员登录
     * 
     * @param name     管理员登录口令
     * @param password 管理员密码
     * @return
     */
    @GetMapping("/in")
    public CustomResponse<String> signIn(
            @RequestParam("name") String name,
            @RequestParam("password") String password) {

        if (name == null || name.isEmpty() || name.isBlank()) {
            return CustomResponse.validException("口令不能为空");
        }

        if (password == null || password.isEmpty() || password.isBlank()) {
            return CustomResponse.validException("密码不能为空");
        }
        String token;
        try {
            token = adminService.generateToken(name, password);
        } catch (Exception ex) {
            return CustomResponse.dbException(ex.getMessage());
        }

        return CustomResponse.success(token);
    }

}