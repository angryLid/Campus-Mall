package io.github.angrylid.mall.mapper;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.angrylid.mall.entity.UnverifiedStudent;

@SpringBootTest
public class UnverifiedStudentMapperTest {

    @Autowired
    UnverifiedStudentMapper unverifiedStudentMapper;

    @Test
    void testGetStudents() {
        List<UnverifiedStudent> students = unverifiedStudentMapper.getStudents();
        Assertions.assertThat(students.size()).isEqualTo(4);
    }
}
