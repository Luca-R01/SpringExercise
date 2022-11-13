package com.project.usermanager.service;

import java.util.List;

import com.project.usermanager.dto.request.car.CarRequestDTOPost;
import com.project.usermanager.dto.request.car.CarRequestDTOPut;
import com.project.usermanager.dto.response.CarResponseDTO;
import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.exception.ConflictException;
import com.project.usermanager.exception.NotFoundException;

public interface CarService {

    void createCar(CarRequestDTOPost requestDTO) throws BadRequestException, ConflictException, NotFoundException;

    CarResponseDTO findCar(String licensePlate) throws NotFoundException;

    List<CarResponseDTO> findAllByOwner(String ownerFiscalCode) throws NotFoundException;

    void editCar(CarRequestDTOPut requestDTO, String licensePlate) throws BadRequestException, NotFoundException;

    void deleteCar(String licensePlate) throws NotFoundException;

    List<CarResponseDTO> findAll();
    
}