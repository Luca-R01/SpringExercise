package com.project.usermanager.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.usermanager.dto.response.UserFullResponseDTO;
import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.exception.NotFoundException;
import com.project.usermanager.mapper.UserFullMapper;
import com.project.usermanager.model.CarEntity;
import com.project.usermanager.model.UserEntity;
import com.project.usermanager.model.deleted.CarEntityDeleted;
import com.project.usermanager.model.deleted.UserEntityDeleted;
import com.project.usermanager.repository.CarRepository;
import com.project.usermanager.repository.UserRepository;
import com.project.usermanager.repository.deleted.CarDeletedRepository;
import com.project.usermanager.repository.deleted.UserDeletedRepository;
import com.project.usermanager.service.UserFullService;
import com.project.usermanager.util.PasswordUtil;

import lombok.Builder;

@Builder
@Service
public class UserFullServiceImpl implements UserFullService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserDeletedRepository userDeletedRepository;

    @Autowired
    private final CarRepository carRepository;

    @Autowired
    private final CarDeletedRepository carDeletedRepository;

    @Autowired
    private final UserFullMapper mapper;

    private static final Logger logger = LoggerFactory.getLogger(UserFullServiceImpl.class);

    @Override
    public UserFullResponseDTO findUser(String username) throws NotFoundException {
        
        logger.info("findUser - IN: username({}) ", username);

        // Find User
        Optional<UserEntity> userRegistry = userRepository.findByUsername(username);
        if (userRegistry.isEmpty()) {
            
            logger.info("findUser - OUT: NotFoundException(User Not Found!) ");
            throw new NotFoundException("User Not Found!");
        }
        // Find User's Cars
        List<CarEntity> carList = carRepository.findAllByOwnerUsername(username);

        UserFullResponseDTO response = mapper.toDTO(userRegistry.get(), carList);

        logger.info("findUser - OUT: {} ", response.toString());
        return response;
    }

    @Override
    public void deleteUserAndCars(String username, String password) throws NotFoundException, BadRequestException {

        logger.info("deleteUserAndCars - IN: username({}) ", username);

        // Control if User with input Username exists
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {

            logger.info("deleteUser - OUT: NotFoundException(User not found!) ");
            throw new NotFoundException("User not found!");
        }

        // Encrypt Password
        String encryptedPassword = PasswordUtil.encryptPassword(password);

        // Control if input Password is correct
        if (! user.get().getPassword().equals(encryptedPassword)) {

            logger.info("deleteUser - OUT: BadRequestException(Password is not correct!) ");
            throw new BadRequestException("Password is not correct!");
        }

        // Find all User's Cars
        List<CarEntity> carList = carRepository.findAllByOwnerUsername(username);

        // Delete Cars
        carRepository.deleteAll(carList);

        // Add Deleted Cars in Deleted DB Collections
        List<CarEntityDeleted> deletedCars = new ArrayList<>();
        for (CarEntity car : carList) {

            CarEntityDeleted carDeleted = CarEntityDeleted.builder()
                .carEntity(car)
            .build();

            deletedCars.add(carDeleted);
        }
        carDeletedRepository.saveAll(deletedCars);

        // Delete User
        userRepository.delete(user.get());

        // Add Deleted User in Deleted DB Collections
        UserEntityDeleted userDeleted = UserEntityDeleted.builder()
            .userEntity(user.get())
        .build();

        userDeletedRepository.save(userDeleted);


        logger.info("deleteUserAndCars - OUT: OK ");
    }
    
}
