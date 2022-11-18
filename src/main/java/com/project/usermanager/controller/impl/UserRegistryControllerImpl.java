package com.project.usermanager.controller.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.project.usermanager.controller.UserRegistryController;
import com.project.usermanager.dto.request.user.UserRequestDTOPost;
import com.project.usermanager.dto.request.user.UserRequestDTOPut;
import com.project.usermanager.dto.response.UserRegistryResponseDTO;
import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.exception.ConflictException;
import com.project.usermanager.exception.NotFoundException;
import com.project.usermanager.service.UserRegistryService;

import lombok.Builder;
@Builder
@RestController
public class UserRegistryControllerImpl implements UserRegistryController {

    @Autowired
    private final UserRegistryService service;

    private static final Logger logger = LoggerFactory.getLogger(UserRegistryControllerImpl.class);

    @Override
    public ResponseEntity<UserRegistryResponseDTO> createUserRegistry(UserRequestDTOPost requestDTO) throws BadRequestException, ConflictException {

        logger.info("createUser - IN: {} ", requestDTO.toString());

        UserRegistryResponseDTO responseDTO = service.createUserRegistry(requestDTO);
        ResponseEntity<UserRegistryResponseDTO> response = new ResponseEntity<>(responseDTO, HttpStatus.CREATED);

        logger.info("createUser - OUT: {} ", response.toString());
        return response;
    }

    @Override
    public ResponseEntity<UserRegistryResponseDTO> findUserRegistry(String fiscalCode) throws BadRequestException, ConflictException, NotFoundException {
        
        logger.info("findUser - IN: fiscalCode({}) ", fiscalCode);

        UserRegistryResponseDTO responseDTO = service.findUserRegistry(fiscalCode);
        ResponseEntity<UserRegistryResponseDTO> response = new ResponseEntity<>(responseDTO, HttpStatus.OK);

        logger.info("findUser - OUT: {} ", response.toString());
        return response;
    }

    @Override
    public ResponseEntity<String> editUserRegistry(UserRequestDTOPut requestDTO, String fiscalCode, String password) throws BadRequestException, NotFoundException, ConflictException {
        
        logger.info("editUser - IN: {}, fiscalCode({}) ", requestDTO.toString(), fiscalCode);

        service.editUserRegistry(requestDTO, fiscalCode, password);
        ResponseEntity<String> response = new ResponseEntity<>("EDITED", HttpStatus.NO_CONTENT);

        logger.info("editUser - OUT: {} ", response.toString());
        return response;
    }

    @Override
    public ResponseEntity<String> deleteUserRegistry(String fiscalCode, String password) throws NotFoundException, BadRequestException {
        
        logger.info("deleteUser - IN: fiscalCode({}) ", fiscalCode);

        service.deleteUserRegistry(fiscalCode, password);
        ResponseEntity<String> response = new ResponseEntity<>("DELETED", HttpStatus.NO_CONTENT);

        logger.info("deleteUser - OUT: {} ", response.toString());
        return response;
    }

    @Override
    public ResponseEntity<List<UserRegistryResponseDTO>> findAllUserRegistry() {

        logger.info("findAll - IN: none ");

        List<UserRegistryResponseDTO> responseDTOList = service.findAllUserRegistry();
        ResponseEntity<List<UserRegistryResponseDTO>> response = new ResponseEntity<>(responseDTOList, HttpStatus.OK);

        logger.info("findAll - OUT: {} ", response.toString());
        return response;
    }
    
}
