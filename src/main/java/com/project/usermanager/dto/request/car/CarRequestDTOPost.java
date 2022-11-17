package com.project.usermanager.dto.request.car;

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
