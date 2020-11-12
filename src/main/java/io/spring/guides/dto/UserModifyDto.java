package io.spring.guides.dto;

import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class UserModifyDto {


    @NotBlank
    protected String department;

    @NotBlank
    protected String position;

    @NonNull
    protected boolean isAdmin;


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
                "dateSeperation=" +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
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
