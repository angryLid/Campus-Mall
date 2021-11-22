package io.github.angrylid.mall.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegisterDto {

    @NotBlank
    @Size(min = 11, max = 11)
    String telephone;

    @NotBlank
    @Size(min = 8, max = 16)
    String password;

    public RegisterDto(@NotBlank @Size(min = 11, max = 11) String telephone,
            @NotBlank @Size(min = 8, max = 16) String password) {
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
