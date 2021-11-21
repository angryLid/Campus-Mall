package io.github.angrylid.mall.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserLoginDto {
    @NotNull
    Long jobNumber;

    @NotBlank
    String password;

    public UserLoginDto(String password, Long jobNumber) {

        this.password = password;
        this.jobNumber = jobNumber;
    }

    public Long getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(Long jobNumber) {
        this.jobNumber = jobNumber;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
