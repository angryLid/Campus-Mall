package io.github.angrylid.mall.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.github.angrylid.mall.dto.BaseImages;

public class PostProductDto extends BaseImages {

    @NotNull
    @NotBlank
    @NotEmpty
    protected String title;

    @NotBlank
    @NotEmpty
    @NotNull
    protected String description;

    @NotBlank
    @NotEmpty
    @NotNull
    protected String price;

    @NotBlank
    @NotEmpty
    @NotNull
    protected String pType;

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
