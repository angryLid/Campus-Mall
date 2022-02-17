package io.github.angrylid.mall.dto;

public class ChatNotification {
    private String telephone;
    private String name;
    private String createdAt;
    private String content;

    public ChatNotification() {
    }

    public ChatNotification(String telephone, String name, String createdAt, String content) {
        this.telephone = telephone;
        this.name = name;
        this.createdAt = createdAt;
        this.content = content;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ChatNotification [content=" + content + ", createdAt=" + createdAt + ", name=" + name + ", telephone="
                + telephone + "]";
    }

}
