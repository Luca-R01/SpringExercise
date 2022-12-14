package com.project.usermanager.controller.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.project.usermanager.component.UserFullServiceComponent;
import com.project.usermanager.controller.UserFullController;
import com.project.usermanager.dto.response.UserFullResponseDTO;
import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.exception.NotFoundException;

import lombok.Builder;

@Builder
@RestController
public class UserFullControllerImpl implements UserFullController {

    @Autowired
    private final UserFullServiceComponent serviceComponent;

    private static final Logger logger = LoggerFactory.getLogger(UserFullControllerImpl.class);

    @Override
    public ResponseEntity<UserFullResponseDTO> findUser(String username) throws NotFoundException {

        logger.info("findUser - IN: username({}) ", username);

        UserFullResponseDTO responseDTO = serviceComponent.findUser(username);
        ResponseEntity<UserFullResponseDTO> response = new ResponseEntity<>(responseDTO, HttpStatus.OK);

        logger.info("findUser - OUT: {} ", response.toString());
        return response;
    }

    @Override
    public ResponseEntity<List<UserFullResponseDTO>> findAllUser() throws NotFoundException {

        logger.info("findAllUser - IN: NONE ");
        
        List<UserFullResponseDTO> responseDTO = serviceComponent.findAllUsers();
        ResponseEntity<List<UserFullResponseDTO>> response = new ResponseEntity<>(responseDTO, HttpStatus.OK);

        logger.info("findAllUser - OUT: {} ", response.toString());
        return response;
    }

    @Override
    public ResponseEntity<String> deleteUserAndCars(String username, String password) throws NotFoundException, BadRequestException {
        
        logger.info("deleteUserAndCars - IN: username({})", username);

        serviceComponent.deleteUserAndCars(username, password);
        ResponseEntity<String> response = new ResponseEntity<>("Deleted", HttpStatus.NO_CONTENT);

        logger.info("deleteUserAndCars - OUT: {}", response.toString());
        return response;
    }
    
}
