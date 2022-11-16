package com.project.usermanager.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "cars")
public class CarEntity {

    @Id
    private ObjectId id;

    @Field("OWNER_FISCAL_CODE")
    private String ownerFiscalCode;

    @Field("LICENSE_PLATE")
    private String licensePlate;
    
    @Field("BRAND")
    private String brand;

    @Field("MODEL")
    private String model;
}
