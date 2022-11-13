package com.project.usermanager.dto.request.user;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserRequestDTOPut {

    @JsonProperty("FISCAL_CODE")
    private String fiscalCode;

    @JsonProperty("NAME")
    private String name;

    @JsonProperty("LAST_NAME")
    private String lastName;

    @JsonProperty("BIRTH_DATE")
    private LocalDate birthDate;

    @Pattern(regexp = "M|F")
    @JsonProperty("GENDER")
    private String gender;

    @Email
    @JsonProperty("EMAIL")
    private String email;

    @JsonProperty("PASSWORD")
    private String password;

    @JsonProperty("CONFIRM_PASSWORD")
    private String confirmPassword;
    
}
