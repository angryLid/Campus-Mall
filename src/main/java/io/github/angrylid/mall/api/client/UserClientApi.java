package io.github.angrylid.mall.api.client;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;

import io.github.angrylid.mall.api.annotation.ClientController;
import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.dto.QualificationDto;
import io.github.angrylid.mall.dto.request.UserNameDTO;
import io.github.angrylid.mall.entity.AccountInformation;
import io.github.angrylid.mall.entity.UnverifiedStudent;
import io.github.angrylid.mall.generated.entity.Qualification;
import io.github.angrylid.mall.generated.entity.Student;
import io.github.angrylid.mall.jwt.annotation.TokenRequired;
import io.github.angrylid.mall.service.UserService;

@Valid
@ClientController("/account")
public class UserClientApi {

    @Autowired
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 设置用户名
     * 
     * @param userNameDTO 用户名
     * @param id
     * @return
     */
    @PostMapping("/name")
    public CustomResponse<?> setName(@RequestBody @Valid UserNameDTO userNameDTO,
            @RequestAttribute("id") Integer id) {
        try {
            userService.updateUserName(id, userNameDTO.getName());
            return CustomResponse.success(userNameDTO.getName());
        } catch (SQLException exception) {
            logger.error("用户名已存在");
            return CustomResponse.dbException("用户名已存在");
        }

    }

    /**
     * 获取当前用户信息
     * 
     * @param id 用户id
     */
    @TokenRequired
    @GetMapping("/myaccount")
    public CustomResponse<?> getMyAccount(@RequestAttribute("id") Integer id) {

        AccountInformation result = userService.selectAccountInfo(id);

        return CustomResponse.success(result);
    }

    /**
     * 根据电话号码获取用户名
     * 
     * @param telephone 电话号码
     * @return
     */
    @TokenRequired
    @GetMapping("/username/{telephone}")
    public CustomResponse<?> getUsername(@PathVariable("telephone") String telephone) {
        String name = userService.selectNameByTelephone(telephone);
        return CustomResponse.success(name);
    }

    /**
     * 获取用户学生信息
     * 
     * @param id
     * @return
     */
    @TokenRequired
    @GetMapping("/student_info/")
    public CustomResponse<Student> getStudentInfo(@RequestAttribute("id") Integer id) {

        Student student = userService.selectStudentInfo(id);
        return CustomResponse.success(student);
    }

    /**
     * 获取用户店铺信息
     * 
     * @param id
     * @return
     */
    @TokenRequired
    @GetMapping("/merchant_info/")
    public CustomResponse<Qualification> getMerchantInfo(@RequestAttribute("id") Integer id) {
        Qualification qualification = userService.selectMerchantInfo(id);
        return CustomResponse.success(qualification);
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
    public CustomResponse<Map<String, ?>> myRoleType(@RequestAttribute("id") Integer id) {
        Map<String, ?> roleType = userService.getUserStatus(id);
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
