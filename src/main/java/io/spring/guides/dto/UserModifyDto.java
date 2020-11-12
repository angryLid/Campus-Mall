package io.spring.guides.dto;

import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class UserModifyDto {
    @NotNull
    protected Date dateSeparation;

    @NotBlank
    protected String department;

    @NotBlank
    protected String position;

    @NonNull
    protected boolean isAdmin;

    public Date getDateSeparation() {
        return dateSeparation;
    }

    public void setDateSeparation(Date dateSeparation) {
        this.dateSeparation = dateSeparation;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    protected String password;

    @Override
    public String toString() {
        return "UserModifyDto{" +
                "dateSeperation=" + dateSeparation +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }

    public UserModifyDto(Date dateSeperation, String department, String position, boolean isAdmin) {
        this.dateSeparation = dateSeperation;
        this.department = department;
        this.position = position;
        this.isAdmin = isAdmin;
    }



    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
