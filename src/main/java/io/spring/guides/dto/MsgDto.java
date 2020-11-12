package io.spring.guides.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MsgDto {
    @NotBlank
    String message;

    @NotNull
    Long applicant;


    public Long getApplicant() {
        return applicant;
    }

    public void setApplicant(Long applicant) {
        this.applicant = applicant;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
