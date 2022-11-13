package com.project.usermanager.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.usermanager.dto.request.car.CarRequestDTOPost;
import com.project.usermanager.dto.request.car.CarRequestDTOPut;
import com.project.usermanager.dto.response.CarResponseDTO;
import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.exception.ConflictException;
import com.project.usermanager.exception.NotFoundException;
import com.project.usermanager.mapper.CarMapper;
import com.project.usermanager.model.CarEntity;
import com.project.usermanager.repository.CarRepository;
import com.project.usermanager.service.CarService;
import com.project.usermanager.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private final CarRepository repository;

    @Autowired
    private final CarMapper mapper;

    @Autowired
    private final UserService userService;

    @Override
    public void createCar(CarRequestDTOPost requestDTO) throws BadRequestException, ConflictException, NotFoundException {

        Optional<CarEntity> findCar = repository.findByLicensePlate(requestDTO.getLicensePlate());
        if (findCar.isPresent()) {
            throw new ConflictException("Car alredy exists!");
        }
        else {
            // Control if user with input FiscalCode exists
            userService.findUser(requestDTO.getOwnerFiscalCode());

            CarEntity car = mapper.toEntity(requestDTO);
            repository.save(car);
        }
    }

    @Override
    public CarResponseDTO findCar(String licensePlate) throws NotFoundException {
        
        Optional<CarEntity> car = repository.findByLicensePlate(licensePlate);
        if (car.isEmpty()) {
            throw new NotFoundException("Car not found!");
        }
        else {
            CarResponseDTO response = mapper.toDTO(car.get());
            return response;
        }
    }

    @Override
    public List<CarResponseDTO> findAllByOwner(String ownerFiscalCode) throws NotFoundException {

        // Control if user with input FiscalCode exists
        userService.findUser(ownerFiscalCode);

        List<CarEntity> carList = repository.findAllByOwnerFiscalCode(ownerFiscalCode);
        List<CarResponseDTO> response = mapper.toDTOList(carList);

        return response;
    }

    @Override
    public void editCar(CarRequestDTOPut requestDTO, String licensePlate) throws BadRequestException, NotFoundException {
        
        Optional<CarEntity> car = repository.findByLicensePlate(licensePlate);
        if (car.isEmpty()) {
            throw new NotFoundException("Car not found!");
        }
        else {
            // Control if user with input FiscalCode exists
            userService.findUser(requestDTO.getOwnerFiscalCode());
            
            CarEntity editCar = mapper.editCar(requestDTO, car.get());
            repository.save(editCar);
        }

        
    }

    @Override
    public void deleteCar(String licensePlate) throws NotFoundException {
        
        Optional<CarEntity> car = repository.findByLicensePlate(licensePlate);
        if (car.isEmpty()) {
            throw new NotFoundException("Car not found!");
        }
        else {
            repository.delete(car.get());
        }
    }

    @Override
    public List<CarResponseDTO> findAll() {
        
        List<CarEntity> carList = repository.findAll();
        List<CarResponseDTO> response = mapper.toDTOList(carList);

        return response;
    }
    
}
