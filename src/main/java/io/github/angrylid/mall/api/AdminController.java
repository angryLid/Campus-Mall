package io.github.angrylid.mall.api;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.generated.entity.User;
import io.github.angrylid.mall.generated.mapper.AdminMapper;
import io.github.angrylid.mall.generated.mapper.UserMapper;
import io.github.angrylid.mall.jwt.JwtUtil;

@RestController()
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    UserMapper userMapper;

    /**
     * 
     * @param name     管理员登录口令
     * @param password 管理员密码
     * @return
     */
    @GetMapping("/")
    public CustomResponse<String> signIn(
            @RequestParam("name") String name,
            @RequestParam("password") String password) {

        if (name == null || name.isEmpty() || name.isBlank()) {
            return CustomResponse.validException("口令不能为空");
        }

        if (password == null || password.isEmpty() || password.isBlank()) {
            return CustomResponse.validException("密码不能为空");
        }

        var result = adminMapper.selectByMap(ofEntries(
                entry("name", name),
                entry("password", password)));

        if (result.size() != 1) {
            return CustomResponse.validException("找不到该用户");
        }

        return CustomResponse.success(JwtUtil.sign(name, password));
    }

    /**
     * 
     * @return 所有用户
     */
    @GetMapping("/user")
    public CustomResponse<Object> getAllUsers() {
        Page<User> page = new Page<>(1, 50);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("created_at");
        Page<User> users = userMapper.selectPage(page, wrapper);

        return CustomResponse.success(users.getRecords());
    }
}
