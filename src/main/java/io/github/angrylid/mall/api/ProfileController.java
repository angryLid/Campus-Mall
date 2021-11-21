package io.github.angrylid.mall.api;

import com.auth0.jwt.JWT;
import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.dto.UserAddDto;
import io.github.angrylid.mall.dto.UserModifyDto;
import io.github.angrylid.mall.jwt.JwtUtil;
import io.github.angrylid.mall.jwt.UserRole;
import io.github.angrylid.mall.jwt.annotation.TokenRequired;
import io.github.angrylid.mall.mbg.entity.User;
import io.github.angrylid.mall.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "用户模块")
@RestController()
@RequestMapping(path = "/profile")
public class ProfileController {

    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @TokenRequired(role = UserRole.ADMIN)
    @ApiOperation("获取所有用户")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataTypeClass = String.class)
    @GetMapping(path = "/all")
    public CustomResponse<List<User>> getCurrent(@RequestParam(value = "pageNum", defaultValue = "1")
                                                 @ApiParam("页码数量") Integer pageNum,
                                                 @RequestParam(value = "pageSize", defaultValue = "3")
                                                 @ApiParam("页码大小") Integer pageSize) {
        List<User> users = this.userService.fetchUsers(pageNum, pageSize);

        return CustomResponse.success(users);

    }

    @TokenRequired(role = UserRole.ADMIN)
    @ApiOperation("根据主键获得指定用户")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataTypeClass = String.class)
    @GetMapping(path = "/{id}")
    public User getProfileById(@PathVariable("id") long id) {
        return this.userService.queryUserById(id);
    }

    @TokenRequired(role = UserRole.STAFF)
    @ApiOperation("获取自己的信息")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataTypeClass = String.class)
    @GetMapping()
    public CustomResponse<User> getSelfInfo(
            @RequestHeader("Authorization") String token) {
        String jobNumber = JWT.decode(token).getClaim("jobNumber").asString();
        User user = userService.queryUserById(Long.parseLong(jobNumber));
        return CustomResponse.success(user);
    }


    @TokenRequired(role = UserRole.ADMIN)
    @ApiOperation("修改用户信息")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataTypeClass = String.class)
    @PutMapping(path = "{id}")
    public CustomResponse<String> modifyUser(@PathVariable("id") Long id,
                                             @RequestBody
                                             @Validated UserModifyDto dto,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                logger.warn("fieldError:{}", fieldError);
            }
            return CustomResponse.validException("输入的参数不正确");
        }
        boolean result = this.userService.modifyUser(
                id,

                dto.getDepartment(),
                dto.getPosition(),
                dto.isAdmin(),
                dto.getPassword()
        );
        if (result) {
            return CustomResponse.success(String.format("编号%d已经修改", id));
        }
        return CustomResponse.dbException("数据访问错误");

    }

    @TokenRequired(role = UserRole.ADMIN)
    @ApiOperation("新增用户")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataTypeClass = String.class)
    @PostMapping()
    public CustomResponse<String> addUser(@RequestBody @Validated UserAddDto dto,
                                          BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                logger.warn("fieldError:{}", fieldError);
            }
            return CustomResponse.validException("输入的参数不正确");
        }
        boolean result = this.userService.addUser(
                dto.getName(),
                dto.getGender(),
                dto.getEntry(),
                dto.getDepartment(),
                dto.getPosition(),
                dto.getAdmin(),
                dto.getPassword()
        );
        if (result) {
            return CustomResponse.success("新用户已经添加");
        }
        return CustomResponse.dbException("数据访问错误");
    }

    @TokenRequired(role = UserRole.ADMIN)
    @ApiOperation("用户标记为离职")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataTypeClass = String.class)
    @DeleteMapping("{id}")
    public CustomResponse<String> fire(@PathVariable("id") Long id) {
        boolean result = this.userService.fireUser(id);
        if (result) {
            return CustomResponse.success("该雇员已解职");
        }
        return CustomResponse.dbException("数据访问出错");
    }
}
