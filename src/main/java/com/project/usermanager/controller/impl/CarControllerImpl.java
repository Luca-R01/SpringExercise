package com.project.usermanager.controller.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.project.usermanager.controller.CarController;
import com.project.usermanager.dto.request.car.CarRequestDTOPost;
import com.project.usermanager.dto.request.car.CarRequestDTOPut;
import com.project.usermanager.dto.response.CarResponseDTO;
import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.exception.ConflictException;
import com.project.usermanager.exception.NotFoundException;
import com.project.usermanager.service.CarService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CarControllerImpl implements CarController {

    @Autowired
    private final CarService service;

    @Override
    public void createCar(@Valid CarRequestDTOPost requestDTO) throws BadRequestException, ConflictException, NotFoundException {
        
        service.createCar(requestDTO);
    }

    @Override
    public ResponseEntity<CarResponseDTO> findCar(String licensePlate) throws NotFoundException {

        CarResponseDTO response = service.findCar(licensePlate);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CarResponseDTO>> findAll(String ownerFiscalCode) throws NotFoundException {

        if (ownerFiscalCode == null) {
            List<CarResponseDTO> response = service.findAll();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            List<CarResponseDTO> response = service.findAllByOwner(ownerFiscalCode);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @Override
    public void editCar(CarRequestDTOPut requestDTO, String licensePlate) throws BadRequestException, NotFoundException {
        
        service.editCar(requestDTO, licensePlate);
    }

    @Override
    public void deleteCar(String licensePlate) throws NotFoundException {
        
        service.deleteCar(licensePlate);
    }
    
}
