package io.spring.guides.api;

import io.spring.guides.mbg.entity.User;
import io.spring.guides.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Profile Controller")
@RestController()
@RequestMapping(path = "/profile")
public class ProfileController {

    @Autowired
    UserService userService;

    @ApiOperation("获取所有用户")
    @GetMapping(path = "/all")
    public List<User> getCurrent(@RequestParam(value = "pageNum", defaultValue = "1")
                                 @ApiParam("页码数量") Integer pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = "3")
                                 @ApiParam("页码大小") Integer pageSize) {
        return this.userService.fetchUsers();
    }

    @ApiOperation("根据主键获得指定用户")
    @GetMapping(path = "/{id}")
    public User getProfileById(@PathVariable("id") long id) {
        return this.userService.queryUserById(id);
    }
}
