package io.github.angrylid.mall.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UploadStudentDTO {

    @NotBlank
    @NotEmpty
    @NotNull
    @JsonProperty("学号")
    private String studentId;

    @NotBlank
    @NotEmpty
    @NotNull
    @JsonProperty("姓名")
    private String name;

    @NotBlank
    @NotEmpty
    @NotNull
    @JsonProperty("电话号码")
    private String telephone;

    @NotBlank
    @NotEmpty
    @NotNull
    @JsonProperty("组织")
    private String belongTo;

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
        return "UploadStudentDTO [belongTo=" + belongTo + ", name=" + name + ", studentId=" + studentId + ", telephone="
                + telephone + "]";
    }

}
