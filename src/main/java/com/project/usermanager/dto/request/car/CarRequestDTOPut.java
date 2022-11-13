package com.project.usermanager.dto.request.car;

import lombok.Data;

@Data
public class CarRequestDTOPut {

    private String ownerFiscalCode;

    private String licensePlate;
    
    private String brand;

    private String model;
    
}
