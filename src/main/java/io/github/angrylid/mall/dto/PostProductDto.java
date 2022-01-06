package io.github.angrylid.mall.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PostProductDto {

    @NotNull
    @NotBlank
    @NotEmpty
    private String title;

    @NotBlank
    @NotEmpty
    @NotNull
    private String description;

    @NotNull
    @Min(0)
    private int price;


    private String[] images;

    public PostProductDto(String title, String description, int price, String[] images) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.images = images;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }
}
