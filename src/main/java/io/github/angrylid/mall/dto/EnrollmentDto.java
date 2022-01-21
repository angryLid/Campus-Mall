package io.github.angrylid.mall.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnrollmentDto {

    @JsonProperty("students")
    private List<EnrollmentStudent> students;

    public List<EnrollmentStudent> getStudents() {
        return students;
    }

    public void setStudents(List<EnrollmentStudent> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "EnrollmentDto [students=" + students + "]";
    }

}
