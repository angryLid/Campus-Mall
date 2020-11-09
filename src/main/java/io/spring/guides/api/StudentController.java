package io.spring.guides.api;

import io.spring.guides.model.Student;
import io.spring.guides.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/{sno}")
    public Student query(@PathVariable("sno") String sno){
        return this.studentService.queryStudentBySno(sno);
    }
}
