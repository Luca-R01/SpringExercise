package com.project.usermanager.dto.request.car;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CarRequestDTOPost {

    @NotBlank
    @JsonProperty("OWNER_FISCAL_CODE")
    private String ownerFiscalCode;

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
