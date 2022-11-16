package com.project.usermanager.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarResponseDTO {

    @JsonProperty("ID")
    private String id;

    @JsonProperty("OWNER_FISCAL_CODE")
    private String ownerFiscalCode;

    @JsonProperty("LICENSE_PLATE")
    private String licensePlate;
    
    @JsonProperty("BRAND")
    private String brand;

    @JsonProperty("MODEL")
    private String model;
    
}
