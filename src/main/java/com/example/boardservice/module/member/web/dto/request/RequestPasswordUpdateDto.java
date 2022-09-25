package com.example.boardservice.module.member.web.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class RequestPasswordUpdateDto {

    @Length(min = 8, max = 50)
    private String newPassword;

    @Length(min = 8, max = 50)
    private String newPasswordConfirm;

}
