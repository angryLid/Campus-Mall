package io.github.angrylid.mall.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserAuthDTO {

    @Size(max = 11, min = 11)
    @NotBlank
    @NotNull
    String telephone;

    @Size(min = 8, max = 16)
    @NotBlank
    @NotNull
    String password;

    public UserAuthDTO(String telephone, String password) {
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
