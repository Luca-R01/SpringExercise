package com.project.usermanager.model;

import java.time.LocalDate;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "users")
public class UserEntity {

    @Id
    private ObjectId id;

    @Field("FISCAL_CODE")
    private String fiscalCode;

    @Field("NAME")
    private String name;

    @Field("LAST_NAME")
    private String lastName;

    @Field("BIRTH_DATE")
    private LocalDate birthDate;

    @Field("GENDER")
    private String gender;

    @Field("EMAIL")
    private String email;

    @Field("PASSWORD")
    private String password;
    
}
