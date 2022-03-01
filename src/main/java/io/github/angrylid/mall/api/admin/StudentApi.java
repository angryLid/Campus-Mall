package io.github.angrylid.mall.api.admin;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.github.angrylid.mall.api.annotation.AdminController;
import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.dto.request.StudentEnrollmentDTO;
import io.github.angrylid.mall.service.StudentService;

@Validated
@AdminController("/student")
public class StudentApi {
    private static final Logger logger = LoggerFactory.getLogger(StudentApi.class);

    private StudentService studentService;

    public StudentApi(@Autowired StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * 新生入学批量登记注册
     * POST /{admin}/student
     * 
     * @param students 学号,姓名,电话号码,班级
     * @return
     */
    @PostMapping
    public CustomResponse<?> postStudents(
            @RequestBody List<@Valid StudentEnrollmentDTO> students) {
        try {
            for (StudentEnrollmentDTO student : students) {
                studentService.insertStudent(student);
            }
            return CustomResponse.success("注册成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return CustomResponse.dbException("注册失败, 请联系管理员");
        }
    }
}