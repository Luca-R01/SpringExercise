package com.project.usermanager.component.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.usermanager.component.UserFullServiceComponent;
import com.project.usermanager.dto.response.UserFullResponseDTO;
import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.exception.NotFoundException;
import com.project.usermanager.mapper.UserFullMapper;
import com.project.usermanager.model.CarEntity;
import com.project.usermanager.model.UserEntity;
import com.project.usermanager.service.CarService;
import com.project.usermanager.service.UserRegistryService;

import lombok.Builder;

@Builder
@Component
public class UserFullServiceComponentImpl implements UserFullServiceComponent {

    @Autowired
    private final UserRegistryService userRegistryService;

    @Autowired
    private final CarService carService;

    @Autowired
    private final UserFullMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(UserFullServiceComponentImpl.class);

    @Override
    public UserFullResponseDTO findUser(String username) throws NotFoundException {

        logger.info("findUser - IN: username({}) ", username);
        
        UserEntity user = userRegistryService.findUserRegistry(username);
        List<CarEntity> carList = carService.findAllByOwner(username);
        UserFullResponseDTO response = mapper.toDTO(user, carList);

        logger.info("findUser - OUT: {} ", response.toString());
        return response;
    }

    @Override
    public void deleteUserAndCars(String username, String password) throws NotFoundException, BadRequestException {

        logger.info("deleteUserAndCars - IN: username({}) ", username);
        
        carService.deleteCar(username, password);
        userRegistryService.deleteUserRegistry(username, password);

        logger.info("deleteUserAndCars - OUT: OK ");
    }
    
}
