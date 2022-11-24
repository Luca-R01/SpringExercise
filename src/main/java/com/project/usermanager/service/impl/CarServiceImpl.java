package com.project.usermanager.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.exception.ConflictException;
import com.project.usermanager.exception.NotFoundException;
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

    private static final Logger logger = LoggerFactory.getLogger(CarServiceImpl.class);

    @Override
    public CarEntity createCar(CarEntity car, String ownerPassword) throws BadRequestException, ConflictException, NotFoundException {

        logger.info("createCar - IN: {} ", car.toString());

        // Control if Car already exists
        Optional<CarEntity> findCar = repository.findByLicensePlate(car.getLicensePlate());
        if (findCar.isPresent()) {

            logger.info("createCar - OUT: ConflictException(Car alredy exists!) ");
            throw new ConflictException("Car alredy exists!");
        }

        // Control if input User exists
        Optional<UserEntity> owner = userRepository.findByUsername(car.getOwnerUsername());
        if (owner.isEmpty()) {

            logger.info("createCar - OUT: NotFoundException(User not found!) ");
            throw new NotFoundException("User not found!");
        }

        // Encrypt password
        String encryptedPassword = PasswordUtil.encryptPassword(ownerPassword);

        // Control if input Password is correct
        if (! owner.get().getPassword().equals(encryptedPassword)) {

            logger.info("deleteUser - OUT: BadRequestException(Password is not correct!) ");
            throw new BadRequestException("Password is not correct!");
        }

        // Create
        car = repository.save(car);

        logger.info("createCar - OUT: {} ", car.toString());
        return car;
    }

    @Override
    public CarEntity findCar(String licensePlate) throws NotFoundException {

        logger.info("findCar - IN: licensePlate({}) ", licensePlate);
        
        // Find Car
        Optional<CarEntity> car = repository.findByLicensePlate(licensePlate);
        if (car.isEmpty()) {

            logger.info("findCar - OUT: NotFoundException(Car not found!) ");
            throw new NotFoundException("Car not found!");
        }

        logger.info("findCar - OUT: {} ", car.get().toString());
        return car.get();
    }

    @Override
    public List<CarEntity> findAllByOwner(String ownerUsername) throws NotFoundException {

        logger.info("findAllByOwner - IN: ownerUsername({}) ", ownerUsername);

        // Control if User with input Username exists
        Optional<UserEntity> owner = userRepository.findByUsername(ownerUsername);
        if (owner.isEmpty()) {

            logger.info("findAllByOwner - OUT: NotFoundException(Owner Not Found) ");
            throw new NotFoundException("Owner Not Found");
        }
        
        // Find car
        List<CarEntity> carList = repository.findAllByOwnerUsername(ownerUsername);

        logger.info("findAllByOwner - OUT: {} ", carList.toString());
        return carList;
    }

    @Override
    public void editCar(CarEntity newCar, String licensePlate, String ownerPassword) throws BadRequestException, NotFoundException, ConflictException {

        logger.info("editCar - IN: {}, licensePlate({}) ", newCar.toString(), licensePlate);
        
        // Find Car
        CarEntity car = this.findCar(licensePlate);

        // Control if LicensePlate in NewCar Not Exists
        if (! car.getLicensePlate().equals(newCar.getLicensePlate())) {

            Optional<CarEntity> check = repository.findByLicensePlate(newCar.getLicensePlate());
            if (check.isPresent()) {

                logger.info("editCar - OUT: ConflictException(Car with input LicensePlate alredy Exists!) ");
                throw new ConflictException("Car with input LicensePlate alredy Exists!");
            }
        }

        // Control if Owner Username in NewCar Exists
        if (! car.getOwnerUsername().equals(newCar.getOwnerUsername())) {

            Optional<UserEntity> check = userRepository.findByUsername(newCar.getOwnerUsername());
            if (check.isEmpty()) {

                logger.info("editCar - OUT: NotFoundException(Username not found!) ");
                throw new NotFoundException("Username not found!");
            }
        }

        // Encrypt password
        String encryptedPassword = PasswordUtil.encryptPassword(ownerPassword);

        // Control if input Password is correct
        Optional<UserEntity> owner = userRepository.findByUsernameAndPassword(car.getOwnerUsername(), encryptedPassword);
        if (owner.isEmpty()) {

            logger.info("createCar - OUT: BadRequestException(Password is not correct!) ");
            throw new BadRequestException("Password is not correct!");
        }

        // Edit Car
        newCar.setId(car.getId());
        repository.save(newCar);

        logger.info("editCar - OUT: {} ", newCar.toString());
    }

    @Override
    public void deleteCar(String licensePlate, String ownerPassword) throws NotFoundException, BadRequestException {
        
        logger.info("deleteCar - IN: licensePlate({}) ", licensePlate);

        // Find Car
        CarEntity car = this.findCar(licensePlate);

        // Encrypt password
        String encryptedPassword = PasswordUtil.encryptPassword(ownerPassword);

        // Control if input Password is correct
        Optional<UserEntity> owner = userRepository.findByUsernameAndPassword(car.getOwnerUsername(), encryptedPassword);
        if (owner.isEmpty()) {

            logger.info("createCar - OUT: BadRequestException(Password is not correct!) ");
            throw new BadRequestException("Password is not correct!");
        }

        // Delete
        repository.delete(car);

        logger.info("deleteCar - OUT: {} ", car.toString());
    }

    @Override
    public List<CarEntity> findAll() {

        logger.info("findAll - IN: none ");
        
        // Find Cars
        List<CarEntity> carList = repository.findAll();

        logger.info("findAll - OUT: {} ", carList.toString());
        return carList;
    }

    @Override
    public void deleteAll(String ownerUsername, String ownerPassword) throws NotFoundException, BadRequestException {

        logger.info("deleteAll - IN: ownerUsername({}) ", ownerUsername);
        
        // Find Cars
        List<CarEntity> carList = this.findAllByOwner(ownerUsername);

        // Encrypt password
        String encryptedPassword = PasswordUtil.encryptPassword(ownerPassword);

        // Control if input Password is correct
        Optional<UserEntity> owner = userRepository.findByUsernameAndPassword(ownerUsername, encryptedPassword);
        if (owner.isEmpty()) {

            logger.info("createCar - OUT: BadRequestException(Password is not correct!) ");
            throw new BadRequestException("Password is not correct!");
        }

        // Delete
        repository.deleteAll(carList);
        
        logger.info("deleteAll - OUT: {} ", carList.toString());
    }
    
}
