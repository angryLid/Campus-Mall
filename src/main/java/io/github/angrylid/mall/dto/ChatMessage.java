package io.github.angrylid.mall.dto;

public class ChatMessage {

    private String senderTel;
    private String recipientTel;
    private String content;
    private String createdAt;

    public ChatMessage() {

    }

    public ChatMessage(String senderTel, String recipientTel, String content, String createdAt) {
        this.senderTel = senderTel;
        this.recipientTel = recipientTel;
        this.content = content;
        this.createdAt = createdAt;
    }

    public String getSenderTel() {
        return senderTel;
    }

    public void setSenderTel(String senderTel) {
        this.senderTel = senderTel;
    }

    public String getRecipientTel() {
        return recipientTel;
    }

    public void setRecipientTel(String recipientTel) {
        this.recipientTel = recipientTel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ChatMessage [content=" + content + ", createdAt=" + createdAt + ", recipientTel=" + recipientTel
                + ", senderTel=" + senderTel + "]";
    }

}
