package io.github.angrylid.mall.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.angrylid.mall.generated.entity.Student;
import io.github.angrylid.mall.generated.mapper.StudentMapper;

@SpringBootTest
public class StudentTest {

    @Autowired
    StudentMapper studentMapper;

    @Test
    void testInsertStudents() {
        Student student = new Student();
        student.setName("斯文");
        student.setStudentId("1802200007");
        student.setBelongTo("计算机二班");
        studentMapper.insert(student);

        student = new Student();
        student.setName("冰冰");
        student.setStudentId("1802200008");
        student.setBelongTo("计算机二班");
        studentMapper.insert(student);

        student = new Student();
        student.setName("王五");
        student.setStudentId("1802200009");
        student.setBelongTo("计算机二班");
        studentMapper.insert(student);

        student = new Student();
        student.setName("赵六");
        student.setStudentId("1802200004");
        student.setBelongTo("计算机二班");
        studentMapper.insert(student);

        student = new Student();
        student.setName("欧阳锋");
        student.setStudentId("1802200005");
        student.setBelongTo("计算机二班");
        studentMapper.insert(student);

        student = new Student();
        student.setName("令狐冲");
        student.setStudentId("1802200006");
        student.setBelongTo("计算机二班");
        studentMapper.insert(student);

        assertThat(studentMapper.selectList(null).size()).isEqualTo(6);

    }

}
