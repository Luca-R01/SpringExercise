package com.project.usermanager.dto.request.user;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class UserRequestDTOPost {

    @NotBlank
    @JsonProperty("USERNAME")
    private String username;

    @NotBlank
    @JsonProperty("NAME")
    private String name;

    @NotBlank
    @JsonProperty("LAST_NAME")
    private String lastName;

    @NotNull
    @JsonProperty("BIRTH_DATE")
    private LocalDate birthDate;

    @NotBlank
    @Pattern(regexp = "M|F")
    @JsonProperty("GENDER")
    private String gender;

    @NotBlank
    @Email
    @JsonProperty("EMAIL")
    private String email;

    @NotBlank
    @JsonProperty("PASSWORD")
    private String password;

    @NotBlank
    @JsonProperty("CONFIRM_PASSWORD")
    private String confirmPassword;
    
}
