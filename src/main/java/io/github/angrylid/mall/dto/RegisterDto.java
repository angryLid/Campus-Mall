package io.github.angrylid.mall.dto;

import javax.validation.constraints.NotBlank;

public class RegisterDto {

    @NotBlank
    String telephone;
    @NotBlank
    String password;

    public RegisterDto(@NotBlank String telephone, @NotBlank String password) {
        this.telephone = telephone;
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
