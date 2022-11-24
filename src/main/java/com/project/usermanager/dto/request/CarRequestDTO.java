package com.project.usermanager.dto.request;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class CarRequestDTO {

    @NotBlank
    @JsonProperty("OWNER_USERNAME")
    private String ownerUsername;

    @NotBlank
    @JsonProperty("LICENSE_PLATE")
    private String licensePlate;
    
    @NotBlank
    @JsonProperty("BRAND")
    private String brand;

    @NotBlank
    @JsonProperty("MODEL")
    private String model;
    
}
