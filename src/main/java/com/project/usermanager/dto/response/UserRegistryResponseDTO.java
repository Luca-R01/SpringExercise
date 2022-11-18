package com.project.usermanager.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class UserRegistryResponseDTO {

    @JsonProperty("ID")
    private String id;

    @JsonProperty("USERNAME")
    private String username;

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
