package com.project.usermanager.dto.request.car;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
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
