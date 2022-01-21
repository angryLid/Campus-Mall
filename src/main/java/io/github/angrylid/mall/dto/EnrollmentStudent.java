package io.github.angrylid.mall.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnrollmentStudent {

    @JsonProperty("学号")
    private String studentId;

    @JsonProperty("姓名")
    private String name;

    @JsonProperty("电话号码")
    private String telephone;

    @JsonProperty("组织")
    private String belongTo;

    public EnrollmentStudent(String studentId, String name, String telephone, String belongTo) {
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

    @Override
    public String toString() {
        return "PreEnrollStudent [belongTo=" + belongTo + ", name=" + name + ", studentId=" + studentId + ", telephone="
                + telephone + "]";
    }

}
