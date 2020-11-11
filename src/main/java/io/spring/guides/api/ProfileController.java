package io.spring.guides.api;

import io.spring.guides.annotation.TokenRequired;
import io.spring.guides.dto.CustomResponse;
import io.spring.guides.mbg.entity.User;
import io.spring.guides.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = "用户模块")
@RestController()
@RequestMapping(path = "/profile")
public class ProfileController {

    @Autowired
    UserService userService;


    @TokenRequired
    @ApiOperation("获取所有用户")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @GetMapping(path = "/all")
    public CustomResponse<List<User>> getCurrent(@RequestParam(value = "pageNum", defaultValue = "1")
                                                 @ApiParam("页码数量") Integer pageNum,
                                                 @RequestParam(value = "pageSize", defaultValue = "3")
                                                 @ApiParam("页码大小") Integer pageSize) {
        List<User> users = this.userService.fetchUsers();

        return CustomResponse.success(users);

    }


    @ApiOperation("根据主键获得指定用户")
    @GetMapping(path = "/{id}")
    public User getProfileById(@PathVariable("id") long id) {
        return this.userService.queryUserById(id);
    }
}
