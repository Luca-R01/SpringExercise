package com.project.usermanager.model;

import java.time.LocalDate;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "users")
public class UserEntity {

    @Id
    private ObjectId id;

    private String fiscalCode;

    private String name;

    private String lastName;

    private LocalDate birthDate;

    private String gender;

    private String email;

    private String password;
    
}
