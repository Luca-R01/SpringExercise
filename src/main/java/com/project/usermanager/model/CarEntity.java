package com.project.usermanager.model;

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
@Document(collection = "cars")
public class CarEntity {

    @Id
    private ObjectId id;

    @Field("OWNER_USERNAME")
    private String ownerUsername;

    @Field("LICENSE_PLATE")
    private String licensePlate;
    
    @Field("BRAND")
    private String brand;

    @Field("MODEL")
    private String model;
}
