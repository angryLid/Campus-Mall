package io.github.angrylid.mall.service;

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
    // private static final Logger logger =
    // LoggerFactory.getLogger(StudentService.class);

    private UserMapper userMapper;
    private StudentMapper studentMapper;

    public StudentService(@Autowired UserMapper userMapper,
            @Autowired StudentMapper studentMapper) {
        this.userMapper = userMapper;
        this.studentMapper = studentMapper;
    }

    /**
     * 新生入学批量登记注册
     * 
     * @param uploadStudent
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertStudent(StudentEnrollmentDTO uploadStudent) {
        // 插入一条学生记录
        Student student = new Student();
        student.setName(uploadStudent.getName());
        student.setBelongTo(uploadStudent.getBelongTo());
        student.setStudentId(uploadStudent.getStudentId());
        studentMapper.insert(student);
        // 插入相应的用户记录
        User user = new User();
        user.setTelephone(uploadStudent.getTelephone());
        user.setPasswd("12345678");
        user.setStudentId(student.getId());
        user.setNickname(uploadStudent.getStudentId() + uploadStudent.getName());
        userMapper.insert(user);
    }
}
