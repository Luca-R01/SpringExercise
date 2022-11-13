package com.project.usermanager.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserResponseDTO {

    @JsonProperty("ID")
    private String id;

    @JsonProperty("FISCAL_CODE")
    private String fiscalCode;

    @JsonProperty("NAME")
    private String name;

    @JsonProperty("LAST_NAME")
    private String lastName;

    @JsonProperty("BIRTH_DATE")
    private LocalDate birthDate;

    @JsonProperty("AGE")
    private Integer age;

    @JsonProperty("GENDER")
    private String gender;

    @JsonProperty("EMAIL")
    private String email;

    
}
