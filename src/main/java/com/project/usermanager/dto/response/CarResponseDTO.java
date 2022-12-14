package com.project.usermanager.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class CarResponseDTO {

    @JsonProperty("ID")
    private String id;

    @JsonProperty("OWNER_USERNAME")
    private String ownerUsername;

    @JsonProperty("LICENSE_PLATE")
    private String licensePlate;
    
    @JsonProperty("BRAND")
    private String brand;

    @JsonProperty("MODEL")
    private String model;
    
}
