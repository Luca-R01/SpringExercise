package com.project.usermanager.dto.request.user;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class UserRequestDTOPut {

    private String fiscalCode;

    private String name;

    private String lastName;

    private LocalDate birthDate;

    @Pattern(regexp = "M|F")
    private String gender;

    @Email
    private String email;

    private String password;

    private String confirmPassword;
    
}
