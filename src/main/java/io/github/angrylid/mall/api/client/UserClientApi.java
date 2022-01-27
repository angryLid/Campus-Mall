package io.github.angrylid.mall.api.client;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.dto.QualificationDto;
import io.github.angrylid.mall.entity.AccountInformation;
import io.github.angrylid.mall.entity.UnverifiedStudent;
import io.github.angrylid.mall.generated.entity.Qualification;
import io.github.angrylid.mall.generated.entity.Student;
import io.github.angrylid.mall.jwt.annotation.TokenRequired;
import io.github.angrylid.mall.service.UserService;

@RestController
@RequestMapping("/client/account/")
public class UserClientApi {

    @Autowired
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

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
