package io.github.angrylid.mall.api.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import io.github.angrylid.mall.api.annotation.AdminController;
import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.generated.entity.User;
import io.github.angrylid.mall.service.UserService;

/**
 * 
 * 后台管理员接口
 */
@AdminController("/user")
public class UserApi {

    private UserService userService;

    @Autowired
    public UserApi(UserService userService) {
        this.userService = userService;
    }

    /**
     * 查询所有用户
     * GET /admin/user
     * 
     * @return 所有用户
     */
    @GetMapping()
    public CustomResponse<?> getUsers() {
        List<User> users = userService.selectUsers();
        return CustomResponse.success(users);
    }

    /**
     * 更新用户状态
     * PUT /admin/user/{id}
     * 
     * @param id         用户ID
     * @param authStatus 用户状态
     * @return 是否成功
     */
    @PutMapping("/{id}/{status}")
    public CustomResponse<?> putUser(@PathVariable("id") Integer id,
            @PathVariable("status") Integer authStatus) {

        Integer result = userService.updateUserStatus(id, authStatus);
        if (result != 1) {
            CustomResponse.validException("更新用户失败");
        }
        return CustomResponse.success("更新用户成功");
    }

    /**
     * 根据电话号码检索用户
     * GET /admin/user/{telephone}
     * 
     * @param telephone 电话号码
     * @return 响应<用户实体>
     */
    @GetMapping("/{telephone}")
    public CustomResponse<?> getUser(@PathVariable("telephone") String telephone) {
        User user = userService.selectUserByTelephone(telephone);

        if (user != null) {
            return CustomResponse.success(user);
        }
        return CustomResponse.dbException("找不到用户");
    }

}
