package io.github.angrylid.mall.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.angrylid.mall.dto.UploadStudentDTO;
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

    // @Transactional(rollbackFor = Exception.class)
    public void insertStudent(UploadStudentDTO student) {

        logger.warn(student.toString());

        Student studentModel = new Student();
        student.setName(student.getName());
        student.setBelongTo(student.getBelongTo());
        student.setStudentId(student.getStudentId());
        studentMapper.insert(studentModel);

        User user = new User();
        user.setTelephone(student.getTelephone());
        user.setPasswd("12345678");
        user.setStudentId(studentModel.getId());
        user.setNickname(student.getName() + student.getTelephone().substring(9, 11));
        userMapper.insert(user);
    }
}
