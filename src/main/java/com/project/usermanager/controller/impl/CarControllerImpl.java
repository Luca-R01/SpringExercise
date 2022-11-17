package com.project.usermanager.controller.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(CarControllerImpl.class);

    @Override
    public ResponseEntity<CarResponseDTO> createCar(CarRequestDTOPost requestDTO, String password) throws BadRequestException, ConflictException, NotFoundException {
        
        logger.info("createCar - IN: {} ", requestDTO.toString());

        CarResponseDTO responseDTO = service.createCar(requestDTO, password);
        ResponseEntity<CarResponseDTO> response = new ResponseEntity<>(responseDTO, HttpStatus.CREATED);

        logger.info("createCar - OUT: {} ", response.toString());
        return response;
    }

    @Override
    public ResponseEntity<CarResponseDTO> findCar(String licensePlate) throws NotFoundException {

        logger.info("findCar - IN: licensePlate({}) ", licensePlate);

        CarResponseDTO responseDTO = service.findCar(licensePlate);
        ResponseEntity<CarResponseDTO> response = new ResponseEntity<>(responseDTO, HttpStatus.OK);

        logger.info("findCar - OUT: {} ", response.toString());
        return response;
    }

    @Override
    public ResponseEntity<List<CarResponseDTO>> findAll(String ownerFiscalCode) throws NotFoundException {

        if (ownerFiscalCode == null) {
            logger.info("findAll - IN: ownerFiscalCode(NULL) ");

            List<CarResponseDTO> responseDTO = service.findAll();
            ResponseEntity<List<CarResponseDTO>> response = new ResponseEntity<>(responseDTO, HttpStatus.OK);

            logger.info("findAll - OUT: {} ", response.toString());
            return response;
        }
        else {
            logger.info("findAll - IN: ownerFiscalCode({}) ", ownerFiscalCode);

            List<CarResponseDTO> responseDTO = service.findAllByOwner(ownerFiscalCode);
            ResponseEntity<List<CarResponseDTO>> response = new ResponseEntity<>(responseDTO, HttpStatus.OK);

            logger.info("findAll - OUT: {} ", response.toString());
            return response;
        }
    }

    @Override
    public ResponseEntity<String> editCar(CarRequestDTOPut requestDTO, String licensePlate, String password) throws BadRequestException, NotFoundException {
        
        logger.info("editCar - IN: {}, licensePlate({}) ", requestDTO.toString(), licensePlate);

        service.editCar(requestDTO, licensePlate, password);
        ResponseEntity<String> response = new ResponseEntity<>("EDITED", HttpStatus.NO_CONTENT);

        logger.info("editCar - OUT: {} ", response.toString());
        return response;
    }

    @Override
    public ResponseEntity<String> deleteCar(String licensePlate, String password) throws NotFoundException {

        logger.info("deleteCar - IN: licensePlate({}) ", licensePlate);
        
        service.deleteCar(licensePlate, password);
        ResponseEntity<String> response = new ResponseEntity<>("DELETED", HttpStatus.NO_CONTENT);

        logger.info("deleteCar - OUT: {} ", response.toString());
        return response;
    }
    
}
