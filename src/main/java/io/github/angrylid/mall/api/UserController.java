package io.github.angrylid.mall.api;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.dto.QualificationDto;
import io.github.angrylid.mall.dto.UserLoginDto;
import io.github.angrylid.mall.entity.AccountInformation;
import io.github.angrylid.mall.entity.UnverifiedStudent;
import io.github.angrylid.mall.jwt.annotation.TokenRequired;
import io.github.angrylid.mall.service.UserService;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/")
    public Boolean ping(@RequestParam("token") String token) {
        return userService.verifyJwt(token);
    }

    @PostMapping("/signin")
    public CustomResponse<String> login(@RequestBody @Validated UserLoginDto userLoginDto,
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

    @PostMapping("/signup")
    public CustomResponse<String> register(@RequestBody @Validated UserLoginDto userLoginDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                logger.warn("fieldError:{}", fieldError);
            }
            return CustomResponse.validException("输入的参数不正确");
        }
        String result;
        try {
            result = this.userService.addUser(userLoginDto.getTelephone(),
                    userLoginDto.getPassword(), "User" + userLoginDto.getTelephone());
        } catch (Exception e) {
            return CustomResponse.unauthorized(e.getMessage());
        }
        return CustomResponse.success(result);
    }

    @TokenRequired
    @GetMapping("/myaccount")
    public CustomResponse<AccountInformation> getCurrentUserInformation(@RequestAttribute("id") String identify) {

        var friend = this.userService.getFollowingAndFollowedOfCurrentUser(Integer.parseInt(identify));

        return CustomResponse.success(friend);
    }

    @GetMapping("/student")
    public CustomResponse<Object> manage() {
        var resp = userService.getUnverifiedStudents();
        return CustomResponse.success(resp);
    }

    @PutMapping("/student")
    public CustomResponse<Object> approve(@RequestBody UnverifiedStudent unverifiedStudent) {
        var result = userService.permitStudent(unverifiedStudent.getUid());
        if (result == 1) {
            return CustomResponse.success("OK");
        } else {
            return CustomResponse.dbException("Error");
        }

    }

    /**
     * 用户查询自己的认证状态
     * 
     * @param id 主键, 解析自jwt
     * @return 用户自己账户的状态
     */
    @TokenRequired
    @GetMapping("/myaccount/roletype")
    public CustomResponse<String> myRoleType(@RequestAttribute("id") Integer id) {
        var roleType = userService.getUserStatus(id);
        return CustomResponse.success(roleType);
    }

    @TokenRequired
    @PostMapping(value = "/myaccount/qualification", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public CustomResponse<String> upload(@ModelAttribute QualificationDto dto,
            @RequestAttribute("id") String id) throws IllegalAccessException, IOException {
        logger.error("image0: {}", dto.getImage0());

        userService.qualificationRequset(dto, Integer.parseInt(id));
        return CustomResponse.success(dto.getEnterpriseName());
    }

    @TokenRequired
    @GetMapping("/myaccount/qualification")
    public CustomResponse<QualificationDto> getQualStatus(@RequestAttribute("id") Integer id) {
        QualificationDto resp;

        resp = userService.qualStatus(id);

        return CustomResponse.success(resp);
    }

}
