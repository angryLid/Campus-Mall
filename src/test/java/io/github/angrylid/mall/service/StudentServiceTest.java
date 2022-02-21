package io.github.angrylid.mall.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.angrylid.mall.dto.UploadStudentDTO;

@SpringBootTest
public class StudentServiceTest {

    private StudentService studentService;

    public StudentServiceTest(@Autowired StudentService studentService) {
        this.studentService = studentService;
    }

    @Test
    void testInsertStudent() {
        UploadStudentDTO student = new UploadStudentDTO();
        student.setName("陶白白");
        student.setStudentId("1501020001");
        student.setBelongTo("考古学系");
        student.setTelephone("15031020003");
        System.out.println(student);

        studentService.insertStudent(student);

    }
}
