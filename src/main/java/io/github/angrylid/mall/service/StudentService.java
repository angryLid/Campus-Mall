package io.github.angrylid.mall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.angrylid.mall.dto.EnrollmentStudent;
import io.github.angrylid.mall.entity.RoleType;
import io.github.angrylid.mall.generated.entity.Student;
import io.github.angrylid.mall.generated.entity.User;
import io.github.angrylid.mall.generated.mapper.StudentMapper;
import io.github.angrylid.mall.generated.mapper.UserMapper;

@Service
public class StudentService {

    private UserMapper userMapper;
    private StudentMapper studentMapper;

    public StudentService(@Autowired UserMapper userMapper, @Autowired StudentMapper studentMapper) {
        this.userMapper = userMapper;
        this.studentMapper = studentMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertStudents(EnrollmentStudent input) {

        User user = new User();
        user.setTelephone(input.getTelephone());
        user.setPasswd("12345678");
        user.setRoleType(RoleType.STUDENT_VERIFIED.getStatus());
        user.setNickname(input.getName() + input.getTelephone().substring(9, 11));
        userMapper.insert(user);

        Student student = new Student();
        student.setName(input.getName());
        student.setBelongTo(input.getBelongTo());
        student.setStudentId(input.getStudentId());
        student.setRelatedUser(user.getId());
        studentMapper.insert(student);

    }
}
