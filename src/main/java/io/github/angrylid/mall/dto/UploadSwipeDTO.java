package io.github.angrylid.mall.dto;

import org.springframework.web.multipart.MultipartFile;

public class UploadSwipeDTO {
    private String title;

    private String detail;

    private MultipartFile image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "UploadSwipeDTO [detail=" + detail + ", image=" + image + ", title=" + title + "]";
    }

}
