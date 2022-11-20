package com.project.usermanager.service;

import java.util.List;

import com.project.usermanager.dto.request.car.CarRequestDTOPost;
import com.project.usermanager.dto.request.car.CarRequestDTOPut;
import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.exception.ConflictException;
import com.project.usermanager.exception.NotFoundException;
import com.project.usermanager.model.CarEntity;

public interface CarService {

    CarEntity createCar(CarRequestDTOPost requestDTO, String ownerPassword) throws BadRequestException, ConflictException, NotFoundException;

    CarEntity findCar(String licensePlate) throws NotFoundException;

    List<CarEntity> findAllByOwner(String ownerUsername) throws NotFoundException;

    void editCar(CarRequestDTOPut requestDTO, String licensePlate, String ownerPassword) throws BadRequestException, NotFoundException, ConflictException;

    void deleteCar(String licensePlate, String ownerPassword) throws NotFoundException, BadRequestException;

    List<CarEntity> findAll();

    void deleteAll(String ownerUsername, String ownerPassword) throws NotFoundException, BadRequestException;
    
}
