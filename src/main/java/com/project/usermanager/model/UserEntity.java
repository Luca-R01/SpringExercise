package com.project.usermanager.model;

import java.time.LocalDate;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Builder
@Getter
@Setter
@ToString
@Document(collection = "users")
public class UserEntity {

    @Id
    private ObjectId id;

    @Field("USERNAME")
    private String username;

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
