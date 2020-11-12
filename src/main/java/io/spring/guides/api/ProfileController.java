package io.spring.guides.api;

import io.spring.guides.jwt.annotation.TokenRequired;
import io.spring.guides.jwt.UserRole;
import io.spring.guides.dto.CustomResponse;
import io.spring.guides.dto.UserAddDto;
import io.spring.guides.dto.UserModifyDto;
import io.spring.guides.mbg.entity.User;
import io.spring.guides.service.UserService;
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
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @GetMapping(path = "/all")
    public CustomResponse<List<User>> getCurrent(@RequestParam(value = "pageNum", defaultValue = "1")
                                                 @ApiParam("页码数量") Integer pageNum,
                                                 @RequestParam(value = "pageSize", defaultValue = "3")
                                                 @ApiParam("页码大小") Integer pageSize) {
        List<User> users = this.userService.fetchUsers(pageNum,pageSize);

        return CustomResponse.success(users);

    }

    @TokenRequired(role = UserRole.ADMIN)
    @ApiOperation("根据主键获得指定用户")
    @GetMapping(path = "/{id}")
    public User getProfileById(@PathVariable("id") long id) {
        return this.userService.queryUserById(id);
    }

    @TokenRequired(role = UserRole.ADMIN)
    @ApiOperation("修改用户信息")
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
                dto.getDateSeparation(),
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
}
