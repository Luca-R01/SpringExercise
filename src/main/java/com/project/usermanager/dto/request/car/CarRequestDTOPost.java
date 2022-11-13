package com.project.usermanager.dto.request.car;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CarRequestDTOPost {

    @NotBlank
    private String ownerFiscalCode;

    @NotBlank
    private String licensePlate;
    
    @NotBlank
    private String brand;

    @NotBlank
    private String model;
    
}
