package com.project.usermanager.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(CarServiceImpl.class);

    @Override
    public void createCar(CarRequestDTOPost requestDTO) throws BadRequestException, ConflictException, NotFoundException {

        logger.info("createCar - IN: {} ", requestDTO.toString());

        Optional<CarEntity> findCar = repository.findByLicensePlate(requestDTO.getLicensePlate());
        if (findCar.isPresent()) {
            logger.info("createCar - OUT: NotFoundException ");
            throw new ConflictException("Car alredy exists!");
        }
        else {
            // Control if user with input FiscalCode exists
            userService.findUser(requestDTO.getOwnerFiscalCode());

            CarEntity car = mapper.toEntity(requestDTO);
            repository.save(car);

            logger.info("createCar - OUT: {} ", car.toString());
        }
    }

    @Override
    public CarResponseDTO findCar(String licensePlate) throws NotFoundException {

        logger.info("findCar - IN: licensePlate({}) ", licensePlate);
        
        Optional<CarEntity> car = repository.findByLicensePlate(licensePlate);
        if (car.isEmpty()) {
            logger.info("findCar - OUT: NotFoundException ");
            throw new NotFoundException("Car not found!");
        }
        else {
            CarResponseDTO response = mapper.toDTO(car.get());

            logger.info("findCar - OUT: {} ", response.toString());
            return response;
        }
    }

    @Override
    public List<CarResponseDTO> findAllByOwner(String ownerFiscalCode) throws NotFoundException {

        logger.info("findAllByOwner - IN: ownerFiscalCode({}) ", ownerFiscalCode);

        // Control if user with input FiscalCode exists
        userService.findUser(ownerFiscalCode);

        List<CarEntity> carList = repository.findAllByOwnerFiscalCode(ownerFiscalCode);
        List<CarResponseDTO> response = mapper.toDTOList(carList);

        logger.info("findAllByOwner - OUT: {} ", response.toString());
        return response;
    }

    @Override
    public void editCar(CarRequestDTOPut requestDTO, String licensePlate) throws BadRequestException, NotFoundException {

        logger.info("editCar - IN: {}, licensePlate({}) ", requestDTO.toString(), licensePlate);
        
        Optional<CarEntity> car = repository.findByLicensePlate(licensePlate);
        if (car.isEmpty()) {
            logger.info("editCar - OUT: NotFoundException ");
            throw new NotFoundException("Car not found!");
        }
        else {
            // Control if user with input FiscalCode exists
            userService.findUser(requestDTO.getOwnerFiscalCode());
            
            CarEntity editCar = mapper.editCar(requestDTO, car.get());
            repository.save(editCar);

            logger.info("editCar - OUT: {} ", editCar.toString());
        }

        
    }

    @Override
    public void deleteCar(String licensePlate) throws NotFoundException {
        
        logger.info("deleteCar - IN: licensePlate({}) ", licensePlate);

        Optional<CarEntity> car = repository.findByLicensePlate(licensePlate);
        if (car.isEmpty()) {
            logger.info("deleteCar - OUT: NotFoundException ");
            throw new NotFoundException("Car not found!");
        }
        else {
            repository.delete(car.get());

            logger.info("deleteCar - OUT: {} ", car.get().toString());
        }
    }

    @Override
    public List<CarResponseDTO> findAll() {

        logger.info("findCar - IN: none ");
        
        List<CarEntity> carList = repository.findAll();
        List<CarResponseDTO> response = mapper.toDTOList(carList);

        logger.info("findCar - OUT: {} ", response.toString());
        return response;
    }
    
}
