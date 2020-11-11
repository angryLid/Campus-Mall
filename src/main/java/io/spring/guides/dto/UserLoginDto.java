package io.spring.guides.dto;

public class UserLoginDto {
    Long jobNumber;
    String username;
    String password;

    public Long getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(Long jobNumber) {
        this.jobNumber = jobNumber;
    }

    public UserLoginDto(String username, String password, Long jobNumber) {
        this.username = username;
        this.password = password;
        this.jobNumber = jobNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
