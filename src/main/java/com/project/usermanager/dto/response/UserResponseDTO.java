package com.project.usermanager.dto.response;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserResponseDTO {

    private String id;

    private String fiscalCode;

    private String name;

    private String lastName;

    private LocalDate birthDate;

    private Integer age;

    private String gender;

    private String email;

    
}
