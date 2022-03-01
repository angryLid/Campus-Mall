package io.github.angrylid.mall.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.angrylid.mall.dto.request.StudentEnrollmentDTO;
import io.github.angrylid.mall.generated.entity.Student;
import io.github.angrylid.mall.generated.entity.User;
import io.github.angrylid.mall.generated.mapper.StudentMapper;
import io.github.angrylid.mall.generated.mapper.UserMapper;

@Service
public class StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private UserMapper userMapper;
    private StudentMapper studentMapper;

    public StudentService(@Autowired UserMapper userMapper, @Autowired StudentMapper studentMapper) {
        this.userMapper = userMapper;
        this.studentMapper = studentMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertStudent(StudentEnrollmentDTO uploadStudent) {

        String salt = "salt";
        logger.warn(uploadStudent.toString());

        Student student = new Student();
        student.setName(uploadStudent.getName());
        student.setBelongTo(uploadStudent.getBelongTo());
        student.setStudentId(uploadStudent.getStudentId());
        studentMapper.insert(student);

        User user = new User();
        user.setTelephone(uploadStudent.getTelephone());
        user.setPasswd("12345678");
        user.setStudentId(student.getId());
        user.setNickname(uploadStudent.getName() + uploadStudent.getTelephone().substring(9, 11) + salt);
        userMapper.insert(user);
    }
}
