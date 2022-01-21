package io.github.angrylid.mall.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StudentEnrollmentMapperTest {
    @Autowired
    StudentEnrollmentMapper studentEnrollmentMapper;

    @Test
    void testInsertStudent() {
        studentEnrollmentMapper.insertStudent();

    }
}
