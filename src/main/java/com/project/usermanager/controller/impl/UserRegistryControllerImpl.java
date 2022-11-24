package com.project.usermanager.controller.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.project.usermanager.component.UserRegistryServiceComponent;
import com.project.usermanager.controller.UserRegistryController;
import com.project.usermanager.dto.request.UserRequestDTO;
import com.project.usermanager.dto.response.UserRegistryResponseDTO;
import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.exception.ConflictException;
import com.project.usermanager.exception.NotFoundException;

import lombok.Builder;
@Builder
@RestController
public class UserRegistryControllerImpl implements UserRegistryController {

    @Autowired
    private final UserRegistryServiceComponent serviceComponent;

    private static final Logger logger = LoggerFactory.getLogger(UserRegistryControllerImpl.class);

    @Override
    public ResponseEntity<UserRegistryResponseDTO> createUserRegistry(UserRequestDTO requestDTO) throws BadRequestException, ConflictException {

        logger.info("createUser - IN: {} ", requestDTO.toString());

        UserRegistryResponseDTO responseDTO = serviceComponent.createUserRegistry(requestDTO);
        ResponseEntity<UserRegistryResponseDTO> response = new ResponseEntity<>(responseDTO, HttpStatus.CREATED);

        logger.info("createUser - OUT: {} ", response.toString());
        return response;
    }

    @Override
    public ResponseEntity<UserRegistryResponseDTO> findUserRegistry(String username) throws BadRequestException, ConflictException, NotFoundException {
        
        logger.info("findUser - IN: username({}) ", username);

        UserRegistryResponseDTO responseDTO = serviceComponent.findUserRegistry(username);
        ResponseEntity<UserRegistryResponseDTO> response = new ResponseEntity<>(responseDTO, HttpStatus.OK);

        logger.info("findUser - OUT: {} ", response.toString());
        return response;
    }

    @Override
    public ResponseEntity<String> editUserRegistry(UserRequestDTO requestDTO, String username, String password) throws BadRequestException, NotFoundException, ConflictException {
        
        logger.info("editUser - IN: {}, username({}) ", requestDTO.toString(), username);

        serviceComponent.editUserRegistry(requestDTO, username, password);
        ResponseEntity<String> response = new ResponseEntity<>("EDITED", HttpStatus.NO_CONTENT);

        logger.info("editUser - OUT: {} ", response.toString());
        return response;
    }

    @Override
    public ResponseEntity<List<UserRegistryResponseDTO>> findAllUserRegistry() {

        logger.info("findAll - IN: none ");

        List<UserRegistryResponseDTO> responseDTOList = serviceComponent.findAllUserRegistry();
        ResponseEntity<List<UserRegistryResponseDTO>> response = new ResponseEntity<>(responseDTOList, HttpStatus.OK);

        logger.info("findAll - OUT: {} ", response.toString());
        return response;
    }
    
}
