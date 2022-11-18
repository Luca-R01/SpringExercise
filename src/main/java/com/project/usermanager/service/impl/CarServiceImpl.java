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
import com.project.usermanager.model.UserEntity;
import com.project.usermanager.repository.CarRepository;
import com.project.usermanager.repository.UserRepository;
import com.project.usermanager.service.CarService;
import com.project.usermanager.util.PasswordUtil;

import lombok.Builder;

@Builder
@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private final CarRepository repository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final CarMapper mapper;

    private static final Logger logger = LoggerFactory.getLogger(CarServiceImpl.class);

    @Override
    public CarResponseDTO createCar(CarRequestDTOPost requestDTO, String ownerPassword) throws BadRequestException, ConflictException, NotFoundException {

        logger.info("createCar - IN: {} ", requestDTO.toString());

        // Control if Car already exists
        Optional<CarEntity> findCar = repository.findByLicensePlate(requestDTO.getLicensePlate());
        if (findCar.isPresent()) {

            logger.info("createCar - OUT: ConflictException ");
            throw new ConflictException("Car alredy exists!");
        }

        // Control if User with input FiscalCode exists
        Optional<UserEntity> owner = userRepository.findByFiscalCode(requestDTO.getOwnerFiscalCode());
        if (owner.isEmpty()) {

            logger.info("createCar - OUT: NotFoundException ");
            throw new NotFoundException("User not found!");
        }

        // Encrypt password
        String encryptedPassword = PasswordUtil.encryptPassword(ownerPassword);

        // Control if input Password is correct
        if (! owner.get().getPassword().equals(encryptedPassword)) {

            logger.info("createCar - OUT: BadRequestException ");
            throw new BadRequestException("Password is not correct!");
        }

        // Create
        CarEntity car = mapper.toEntity(requestDTO);
        car = repository.save(car);

        logger.info("createCar - OUT: {} ", car.toString());
        return mapper.toDTO(car);
    }

    @Override
    public CarResponseDTO findCar(String licensePlate) throws NotFoundException {

        logger.info("findCar - IN: licensePlate({}) ", licensePlate);
        
        // Find Car
        Optional<CarEntity> car = repository.findByLicensePlate(licensePlate);
        if (car.isEmpty()) {

            logger.info("findCar - OUT: NotFoundException ");
            throw new NotFoundException("Car not found!");
        }
        CarResponseDTO response = mapper.toDTO(car.get());

        logger.info("findCar - OUT: {} ", response.toString());
        return response;
    }

    @Override
    public List<CarResponseDTO> findAllByOwner(String ownerFiscalCode) throws NotFoundException {

        logger.info("findAllByOwner - IN: ownerFiscalCode({}) ", ownerFiscalCode);

        // Control if User with input FiscalCode exists
        Optional<UserEntity> owner = userRepository.findByFiscalCode(ownerFiscalCode);
        if (owner.isEmpty()) {

            logger.info("findAllByOwner - OUT: NotFoundException ");
            throw new NotFoundException("Owner Not Found");
        }
        // Find car
        List<CarEntity> carList = repository.findAllByOwnerFiscalCode(ownerFiscalCode);
        List<CarResponseDTO> response = mapper.toDTOList(carList);

        logger.info("findAllByOwner - OUT: {} ", response.toString());
        return response;
    }

    @Override
    public void editCar(CarRequestDTOPut requestDTO, String licensePlate, String ownerPassword) throws BadRequestException, NotFoundException, ConflictException {

        logger.info("editCar - IN: {}, licensePlate({}) ", requestDTO.toString(), licensePlate);
        
        // Find Car
        Optional<CarEntity> car = repository.findByLicensePlate(licensePlate);
        if (car.isEmpty()) {

            logger.info("editCar - OUT: NotFoundException ");
            throw new NotFoundException("Car not found!");
        }

        // Control if User with input FiscalCode exists
        Optional<UserEntity> owner = userRepository.findByFiscalCode(car.get().getOwnerFiscalCode());
        if (owner.isEmpty()) {

            logger.info("createCar - OUT: NotFoundException ");
            throw new NotFoundException("User not found!");
        }

        // Control if LicensePlate in DTO Not Exists
        if (requestDTO.getLicensePlate() != null) {

            Optional<CarEntity> check = repository.findByLicensePlate(requestDTO.getLicensePlate());
            if (check.isPresent()) {

                logger.info("editCar - OUT: ConflictException ");
                throw new ConflictException("Car with input LicensePlate alredy Exists!");
            }
        }

        // Encrypt password
        String encryptedPassword = PasswordUtil.encryptPassword(ownerPassword);

        // Control if input Password is correct
        if (! owner.get().getPassword().equals(encryptedPassword)) {

            logger.info("createCar - OUT: BadRequestException ");
            throw new BadRequestException("Password is not correct!");
        }

        // Edit Car
        CarEntity editCar = mapper.editCar(requestDTO, car.get());
        repository.save(editCar);

        logger.info("editCar - OUT: {} ", editCar.toString());
    }

    @Override
    public void deleteCar(String licensePlate, String ownerPassword) throws NotFoundException, BadRequestException {
        
        logger.info("deleteCar - IN: licensePlate({}) ", licensePlate);

        // Find Car
        Optional<CarEntity> car = repository.findByLicensePlate(licensePlate);
        if (car.isEmpty()) {

            logger.info("deleteCar - OUT: NotFoundException ");
            throw new NotFoundException("Car not found!");
        }

        // Control if User with input FiscalCode exists
        Optional<UserEntity> owner = userRepository.findByFiscalCode(car.get().getOwnerFiscalCode());
        if (owner.isEmpty()) {

            logger.info("createCar - OUT: NotFoundException ");
            throw new NotFoundException("User not found!");
        }

        // Encrypt password
        String encryptedPassword = PasswordUtil.encryptPassword(ownerPassword);

        if (! owner.get().getPassword().equals(encryptedPassword)) {

            logger.info("createCar - OUT: BadRequestException ");
            throw new BadRequestException("Password is not correct!");
        }

        // Delete
        repository.delete(car.get());

        logger.info("deleteCar - OUT: {} ", car.get().toString());
    }

    @Override
    public List<CarResponseDTO> findAll() {

        logger.info("findAll - IN: none ");
        
        // Find Cars
        List<CarEntity> carList = repository.findAll();
        List<CarResponseDTO> response = mapper.toDTOList(carList);

        logger.info("findAll - OUT: {} ", response.toString());
        return response;
    }
    
}
