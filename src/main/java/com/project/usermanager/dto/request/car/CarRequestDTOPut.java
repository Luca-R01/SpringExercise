package com.project.usermanager.dto.request.car;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarRequestDTOPut {

    @JsonProperty("OWNER_FISCAL_CODE")
    private String ownerFiscalCode;

    @JsonProperty("LICENSE_PLATE")
    private String licensePlate;
    
    @JsonProperty("BRAND")
    private String brand;

    @JsonProperty("MODEL")
    private String model;
    
}
