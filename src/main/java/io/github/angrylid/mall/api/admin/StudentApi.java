package io.github.angrylid.mall.api.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.github.angrylid.mall.api.annotation.AdminController;
import io.github.angrylid.mall.dto.EnrollmentDto;
import io.github.angrylid.mall.dto.EnrollmentStudent;
import io.github.angrylid.mall.service.StudentService;

@AdminController("/student")
public class StudentApi {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private StudentService studentService;

    public StudentApi(@Autowired StudentService studentService) {
        this.studentService = studentService;
    }

    private boolean isBen(String target) {
        return target == null || target.isEmpty() || target.isBlank();
    }

    /**
     * 新生入学批量登记注册
     * 
     * @param enrollmentDto 学号,姓名,电话号码,班级
     * @return
     */
    @PostMapping("/enrollment")
    public String doEnrollment(@RequestBody EnrollmentDto enrollmentDto) {

        logger.error("enrollment");

        for (EnrollmentStudent s : enrollmentDto.getStudents()) {
            if (isBen(s.getStudentId()) || isBen(s.getName()) || isBen(s.getTelephone()) || isBen(s.getBelongTo())) {
                continue;
            }
            studentService.insertStudents(s);
        }

        return "enrollment";
    }
}
