package io.github.angrylid.mall.api;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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
     * 管理员登录
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
     * 查询所有用户
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

    /**
     * 更新用户状态
     * 
     * @param id         用户ID
     * @param authStatus 用户状态
     * @return 是否成功
     */
    @PutMapping("/user/{id}")
    public CustomResponse<String> updateUserStatus(@PathVariable("id") Long id,
            @RequestParam("authStatus") int authStatus) {
        User user = new User();
        user.setId(id);
        user.setAuthStatus(authStatus);
        int result = userMapper.updateById(user);

        if (result != 1) {
            CustomResponse.validException("更新用户失败");
        }
        return CustomResponse.success("更新用户成功");
    }

    @GetMapping("/user/{telephone}")
    public CustomResponse<User> getSpecificUser(@PathVariable("telephone") String telephone) {
        List<User> users = userMapper.selectByMap(ofEntries(entry("telephone", telephone)));
        if (users.size() != 1) {
            CustomResponse.dbException("找不到用户");
        }
        return CustomResponse.success(users.get(0));
    }

}
