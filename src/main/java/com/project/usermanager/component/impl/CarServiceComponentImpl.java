package com.project.usermanager.component.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.usermanager.component.CarServiceComponent;
import com.project.usermanager.dto.request.car.CarRequestDTOPost;
import com.project.usermanager.dto.request.car.CarRequestDTOPut;
import com.project.usermanager.dto.response.CarResponseDTO;
import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.exception.ConflictException;
import com.project.usermanager.exception.NotFoundException;
import com.project.usermanager.mapper.CarMapper;
import com.project.usermanager.model.CarEntity;
import com.project.usermanager.service.CarService;

import lombok.Builder;

@Builder
@Component
public class CarServiceComponentImpl implements CarServiceComponent {

    @Autowired
    private final CarService service;

    @Autowired
    private final CarMapper mapper;

    private static final Logger logger = LoggerFactory.getLogger(CarServiceComponentImpl.class);

    @Override
    public CarResponseDTO createCar(CarRequestDTOPost requestDTO, String ownerPassword) throws BadRequestException, ConflictException, NotFoundException {

        logger.info("createCar - IN: {} ", requestDTO.toString());

        CarEntity car = service.createCar(requestDTO, ownerPassword);
        CarResponseDTO response = mapper.toDTO(car);

        logger.info("createCar - OUT: {} ", response.toString());
        return response;
    }

    @Override
    public CarResponseDTO findCar(String licensePlate) throws NotFoundException {

        logger.info("findCar - IN: licensePlate({}) ", licensePlate);
        
        CarEntity car = service.findCar(licensePlate);
        CarResponseDTO response = mapper.toDTO(car);

        logger.info("findCar - OUT: {} ", response.toString());
        return response;
    }

    @Override
    public List<CarResponseDTO> findAllByOwner(String ownerUsername) throws NotFoundException {

        logger.info("findAllByOwner - IN: ownerUsername({}) ", ownerUsername);
       
        List<CarEntity> carList = service.findAllByOwner(ownerUsername);
        List<CarResponseDTO> response = mapper.toDTOList(carList);

        logger.info("findAllByOwner - OUT: {} ", response.toString());
        return response;
    }

    @Override
    public void editCar(CarRequestDTOPut requestDTO, String licensePlate, String ownerPassword) throws BadRequestException, NotFoundException, ConflictException {
        
        logger.info("editCar - IN: {}, licensePlate({}) ", requestDTO.toString(), licensePlate);

        service.editCar(requestDTO, licensePlate, ownerPassword);

        logger.info("editCar - OUT: OK");
    }

    @Override
    public void deleteCar(String licensePlate, String ownerPassword) throws NotFoundException, BadRequestException {
        
        logger.info("deleteCar - IN: licensePlate({}) ", licensePlate);

        service.deleteCar(licensePlate, ownerPassword);

        logger.info("deleteCar - OUT: OK");
    }

    @Override
    public List<CarResponseDTO> findAll() {

        logger.info("deleteCar - IN: NONE ");
        
        List<CarEntity> carList = service.findAll();
        List<CarResponseDTO> response = mapper.toDTOList(carList);

        logger.info("deleteCar - OUT: {} ", response.toString());
        return response;
    }
    
}
