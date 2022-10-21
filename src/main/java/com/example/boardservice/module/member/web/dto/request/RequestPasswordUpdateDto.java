package com.example.boardservice.module.member.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class RequestPasswordUpdateDto {

    @Length(min = 8, max = 50)
    @NotEmpty
    @JsonProperty("new_password")
    private String newPassword;

    @Length(min = 8, max = 50)
    @NotEmpty
    @JsonProperty("new_password_confirm")
    private String newPasswordConfirm;

}
