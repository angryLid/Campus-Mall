package io.github.angrylid.mall.dto.request;

import java.time.LocalDateTime;

import io.github.angrylid.mall.dto.BaseImages;

public class QualificationDto extends BaseImages {
    private String enterpriseName;
    private String enterpriseType;
    private String currentStatus;
    private LocalDateTime createdAt;
    private String commentary;

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getEnterpriseType() {
        return enterpriseType;
    }

    public void setEnterpriseType(String enterpriseType) {
        this.enterpriseType = enterpriseType;
    }

    @Override
    public String toString() {
        return "EnterpriseQualification [enterpriseName=" + enterpriseName + ", enterpriseType=" + enterpriseType + "]";
    }

}
