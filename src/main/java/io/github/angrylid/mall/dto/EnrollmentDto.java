package io.github.angrylid.mall.dto;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnrollmentDto {

    public EnrollmentDto(PreEnrollStudent[] students) {
        this.students = students;
    }

    @JsonProperty("students")
    private PreEnrollStudent[] students;

    public PreEnrollStudent[] getStudents() {
        return students;
    }

    public void setStudents(PreEnrollStudent[] students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "EnrollmentDto [students=" + Arrays.toString(students) + "]";
    }

}

class PreEnrollStudent {

    @JsonProperty("studentId")
    private String studentId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("telephone")
    private String telephone;

    @JsonProperty("belongTo")
    private String belongTo;

    public PreEnrollStudent(String studentId, String name, String telephone, String belongTo) {
        this.studentId = studentId;
        this.name = name;
        this.telephone = telephone;
        this.belongTo = belongTo;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }

}
